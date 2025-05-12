package com.sooum.data.repository

import com.sooum.data.extension.covertApiResultToActionResultIfSuccess
import com.sooum.data.extension.covertApiResultToActionResultIfSuccessEmpty
import com.sooum.domain.datasource.FriendRemoteDataSource
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.Friend
import com.sooum.domain.repository.FriendRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UserRepository 구현체
 * @see[FriendRepository]
 */
class FriendRepositoryImpl @Inject constructor(
    private val friendRemoteDataSource: FriendRemoteDataSource
) : FriendRepository {

    // 초기 더미 데이터
    private val _friendListFlow = MutableStateFlow(
        emptyList<Friend>()
    )
    private val friendListFlow: StateFlow<List<Friend>> = _friendListFlow.asStateFlow()

    override fun getFriendList(): Flow<List<Friend>> {
        return friendListFlow
    }

    private val asyncScope = CoroutineScope(Dispatchers.IO)

    override fun loadFriend(userId: Int) {
        asyncScope.launch {
            val friendList = friendRemoteDataSource.getFriendList(userId).first()
            _friendListFlow.update {
                friendList
            }
        }
    }

    override fun getFriendById(friendId: Int): Flow<Friend?> = friendListFlow
        .map { list -> list.find { it.id == friendId } }


    override suspend fun updateFriendFavorite(friendId: Int, userId: Int): ActionResult<*> {
        return friendRemoteDataSource.toggleBookmark(
            userId = userId,
            friendId = friendId
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccess(
                onSuccess = { bookmark ->
                    _friendListFlow.update { friendList ->
                        val index = friendList.indexOfFirst { it.id == friendId }
                        if (index >= 0) {
                            val tempList = friendList.toMutableList()
                            val tempFriend = tempList[index].copy(
                                isFavorite = bookmark.isFavorite
                            )
                            tempList[index] = tempFriend
                            tempList
                        } else {
                            friendList
                        }
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()
    }

    override suspend fun deleteFriend(friendId: Int, userId: Int): ActionResult<*> {
        return friendRemoteDataSource.deleteFriend(
            userId = userId,
            friendId = friendId
        ).transform { result ->
            result.covertApiResultToActionResultIfSuccessEmpty(
                onSuccess = {
                    _friendListFlow.update { friendList ->
                        val index = friendList.indexOfFirst { it.id == friendId }

                        if (index >= 0) {
                            val tempList = friendList.toMutableList()
                            tempList.removeIf { it.id == friendId }
                            tempList
                        } else {
                            friendList
                        }
                    }
                    emit(ActionResult.Success(Unit))
                },
                onFail = {
                    emit(ActionResult.Fail(it))
                }
            )
        }.first()

    }

}