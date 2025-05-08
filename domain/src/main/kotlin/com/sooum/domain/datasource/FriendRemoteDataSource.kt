package com.sooum.domain.datasource

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.Friend
import com.sooum.domain.model.FriendBookMark
import kotlinx.coroutines.flow.Flow

interface FriendRemoteDataSource {

    suspend fun getFriendList(
        userId: Int
    ): Flow<List<Friend>>

    suspend fun toggleBookmark(
        userId: Int,
        friendId: Int
    ): Flow<ApiResult<FriendBookMark>>
}