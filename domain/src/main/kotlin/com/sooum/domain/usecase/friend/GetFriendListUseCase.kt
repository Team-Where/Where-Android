package com.sooum.domain.usecase.friend

import com.sooum.domain.model.Friend
import com.sooum.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFriendListUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    operator fun invoke(): Flow<List<Friend>> {
        return repository.getFriendList()
    }
}