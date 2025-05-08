package com.sooum.data.repository

import com.sooum.domain.datasource.FriendRemoteDataSource
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UserRepository 구현체
 * @see[FriendRepository]
 */
class FriendRepositoryImpl @Inject constructor(
    private val friendRemoteDataSourceImpl: FriendRemoteDataSource
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
            val friendList = friendRemoteDataSourceImpl.getFriendList(userId).first()
            _friendListFlow.update {
                friendList
            }
        }
    }

    override fun getFriendById(friendId: Int): Flow<Friend?> = friendListFlow
        .map { list -> list.find { it.id == friendId } }


    override suspend fun updateFriendFavorite(id: Int, favorite: Boolean) {
        _friendListFlow.update { friendList ->
            val index = friendList.indexOfFirst { it.id == id }
            if (index >= 0) {
                val tempList = friendList.toMutableList()
                val tempFriend = friendList[index].copy(
                    isFavorite = favorite
                )
                tempList[index] = tempFriend
                tempList
            } else {
                friendList
            }
        }
    }

    override suspend fun deleteFriend(id: Int) {
        _friendListFlow.update { friendList ->
            val items = friendList.toMutableList()
            items.removeIf { it.id == id }
            items
        }
    }

}