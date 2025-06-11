package com.sooum.domain.usecase.auth

import com.sooum.domain.model.ApiResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CheckEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Flow<ApiResult<Unit>>{
        return authRepository.checkEmail(email)
    }
}