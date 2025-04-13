package com.sooum.data.repository

import com.sooum.data.network.auth.AuthApi
import com.sooum.data.network.auth.request.LoginRequest
import com.sooum.data.network.auth.request.SignUpRequest
import com.sooum.data.network.meet.MeetApi
import com.sooum.data.network.safeFlow
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.SignUpResult
import com.sooum.domain.repository.AuthRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
) : AuthRepository{

    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        profileImage: String
    ): Flow<ApiResult<SignUpResult>> {
        val request = SignUpRequest(
            email, password, name, profileImage
        )
       return safeFlow { authApi.signUp(request) }
    }

    override suspend fun login(email: String, password: String): Flow<ApiResult<Any>> {
       val request = LoginRequest(
           email, password
       )
        return safeFlow { authApi.login(request) }
    }
}