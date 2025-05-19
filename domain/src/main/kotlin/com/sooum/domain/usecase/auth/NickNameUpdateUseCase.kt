package com.sooum.domain.usecase.auth

import com.sooum.domain.model.ApiResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class NickNameUpdateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: Int, nickName: String): Flow<ApiResult<Unit>>{
        return authRepository.putNickName(userId, nickName)
    }
}