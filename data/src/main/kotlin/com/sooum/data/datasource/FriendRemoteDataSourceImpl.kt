package com.sooum.data.datasource

import com.sooum.data.network.friend.FriendApi
import com.sooum.data.network.friend.request.BookMarkFriendRequest
import com.sooum.data.network.safeFlow
import com.sooum.domain.datasource.FriendRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Friend
import com.sooum.domain.model.FriendBookMark
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendRemoteDataSourceImpl @Inject constructor(
    private val friendApi: FriendApi
) : FriendRemoteDataSource {

    override suspend fun getFriendList(userId: Int): Flow<List<Friend>> = flow {
        val apiResult = safeFlow { friendApi.getFriendList(userId) }.first()
        if (apiResult is ApiResult.Success) {
            emit(apiResult.data)
        } else {
            emit(emptyList())
        }
    }

    override suspend fun toggleBookmark(
        userId: Int,
        friendId: Int
    ): Flow<ApiResult<FriendBookMark>> {
        val request = BookMarkFriendRequest(
            userId = userId,
            friendId = friendId
        )
        return safeFlow { friendApi.bookMarkFriend(request) }
    }
}