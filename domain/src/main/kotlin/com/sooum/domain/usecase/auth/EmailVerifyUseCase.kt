package com.sooum.domain.usecase.auth

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.EmailVerifyResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class EmailVerifyUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke( email: String, code: String): Flow<ApiResult<String>>{
        return authRepository.postEmailVerify(email, code)
    }
}