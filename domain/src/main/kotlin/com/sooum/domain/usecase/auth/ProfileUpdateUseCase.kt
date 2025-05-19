package com.sooum.domain.usecase.auth

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.PostProfileResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import java.io.File

class ProfileUpdateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: Int, imageFile: File?): Flow<ApiResult<PostProfileResult>> {
        return authRepository.postProfileImage(userId, imageFile)
    }
}