package com.sooum.domain.usecase

import com.sooum.domain.model.User
import com.sooum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(id: Int): User? {
        return repository.getUserById(id)
    }
}