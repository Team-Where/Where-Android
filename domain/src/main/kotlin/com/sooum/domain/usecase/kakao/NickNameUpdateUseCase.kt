package com.sooum.domain.usecase.kakao

import com.sooum.domain.model.ApiResult
import com.sooum.domain.repository.SocialLoginRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class NickNameUpdateUseCase @Inject constructor(
    private val kakaoRepository: SocialLoginRepository
) {
    suspend operator fun invoke(userId: Int, nickName: String): Flow<ApiResult<Unit>>{
        return kakaoRepository.putNickName(userId, nickName)
    }
}