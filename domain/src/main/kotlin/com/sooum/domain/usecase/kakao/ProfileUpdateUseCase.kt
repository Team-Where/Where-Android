package com.sooum.domain.usecase.kakao

import android.net.Uri
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.PostProfileResult
import com.sooum.domain.repository.SocialLoginRepository
import com.sooum.domain.util.UriConverter
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ProfileUpdateUseCase @Inject constructor(
    private val kakaoRepository: SocialLoginRepository,
    private val uriConverter: UriConverter
) {
    suspend operator fun invoke(userId: Int, imageFile: Uri): Flow<ApiResult<PostProfileResult>> {
        val file = uriConverter.saveContentUriToTempFile(imageFile)
        return kakaoRepository.postProfileImage(userId, file)
    }
}