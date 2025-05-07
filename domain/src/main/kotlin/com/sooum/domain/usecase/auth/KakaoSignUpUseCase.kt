package com.sooum.domain.usecase.auth

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class KakaoSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>> {
        return authRepository.kakaoLogin(accessToken, refreshToken)
    }
}
