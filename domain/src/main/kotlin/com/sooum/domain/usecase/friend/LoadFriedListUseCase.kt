package com.sooum.domain.usecase.friend

import com.sooum.domain.repository.FriendRepository
import javax.inject.Inject

class LoadFriedListUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(userId: Int) {
        repository.loadFriend(userId)
    }
}