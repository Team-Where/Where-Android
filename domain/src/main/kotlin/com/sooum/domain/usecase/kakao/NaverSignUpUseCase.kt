package com.sooum.domain.usecase.kakao

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.auth.SocialSignUpResult
import com.sooum.domain.repository.SocialLoginRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class NaverSignUpUseCase @Inject constructor(
    private val socialLoginRepository: SocialLoginRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<SocialSignUpResult>> {
        return socialLoginRepository.naverLogin(accessToken, refreshToken)
    }
}