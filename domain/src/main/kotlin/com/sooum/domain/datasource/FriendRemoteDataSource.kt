package com.sooum.domain.datasource

import com.sooum.domain.model.Friend
import kotlinx.coroutines.flow.Flow

interface FriendRemoteDataSource {

    suspend fun getFriendList(
        userId: Int
    ): Flow<List<Friend>>
}