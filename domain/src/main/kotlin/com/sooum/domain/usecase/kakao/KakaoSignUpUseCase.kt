package com.sooum.domain.usecase.kakao

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.auth.SocialSignUpResult
import com.sooum.domain.repository.SocialLoginRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class KakaoSignUpUseCase @Inject constructor(
    private val kakaoRepository: SocialLoginRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<SocialSignUpResult>> {
        return kakaoRepository.kakaoLogin(accessToken, refreshToken)
    }
}
