package com.sooum.domain.usecase.meet

import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.SignUp
import com.sooum.domain.model.SignUpResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke( email: String, password: String, name: String, profileImage: String): Flow<ApiResult<SignUpResult>> {
        return authRepository.signUp(email, password, name, profileImage)
    }
}