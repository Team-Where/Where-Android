package com.sooum.domain.usecase.auth

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<ApiResult<LoginResult>> {
        return authRepository.login(email, password)
    }
}