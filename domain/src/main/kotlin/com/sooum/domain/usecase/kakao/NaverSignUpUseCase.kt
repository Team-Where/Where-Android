package com.sooum.domain.usecase.kakao

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.repository.KakaoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class NaverSignUpUseCase @Inject constructor(
    private val kakaoRepository: KakaoRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ): Flow<ApiResult<KakaoSignUpResult>>{
        return kakaoRepository.naverLogin(accessToken, refreshToken)
    }
}