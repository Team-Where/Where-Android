package com.sooum.domain.usecase.friend

import com.sooum.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendByIdUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    operator fun invoke(id: Int) = repository.getFriendById(id)

}