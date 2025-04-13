package com.sooum.data.network.auth

import com.sooum.data.network.auth.request.CheckEmailRequest
import com.sooum.data.network.auth.request.LoginRequest
import com.sooum.data.network.auth.request.SignUpRequest
import com.sooum.data.network.meet.request.InviteMeetRequest
import com.sooum.domain.model.SignUpResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/user/signup")
    suspend fun signUp(
        @Body data: SignUpRequest
    ): Response<SignUpResult>

    @POST("api/user/login")
    suspend fun login(
        @Body data: LoginRequest
    ): Response<Any>

    @POST("api/user/checkEmail")
    suspend fun checkEmail(
        @Body data: CheckEmailRequest
    ): Response<Any>
}