package com.sooum.domain.usecase.friend

import com.sooum.domain.repository.FriendRepository
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(id: Int) {
        return repository.deleteFriend(id)
    }
}