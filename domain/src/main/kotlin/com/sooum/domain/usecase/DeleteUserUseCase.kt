package com.sooum.domain.usecase

import com.sooum.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) {
        return repository.deleteUser(id)
    }
}