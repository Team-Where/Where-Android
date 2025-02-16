package com.sooum.domain.usecase

import com.sooum.domain.model.User
import com.sooum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getUserList()
    }
}