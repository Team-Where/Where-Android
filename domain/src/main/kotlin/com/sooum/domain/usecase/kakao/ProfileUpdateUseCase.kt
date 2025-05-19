package com.sooum.domain.usecase.kakao

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.PostProfileResult
import com.sooum.domain.repository.KakaoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import java.io.File

class ProfileUpdateUseCase @Inject constructor(
    private val kakaoRepository: KakaoRepository
) {
    suspend operator fun invoke(userId: Int, imageFile: File?): Flow<ApiResult<PostProfileResult>> {
        return kakaoRepository.postProfileImage(userId, imageFile)
    }
}