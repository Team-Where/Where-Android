package com.sooum.data.network.auth

import com.sooum.data.network.auth.request.CheckEmailRequest
import com.sooum.data.network.auth.request.EmailVerifyRequest
import com.sooum.data.network.auth.request.LoginRequest
import com.sooum.data.network.auth.request.SignUpRequest
import com.sooum.domain.model.EmailVerifyResult
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.SignUpResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi {
    @POST("api/user/signup")
    suspend fun signUp(
        @Body data: SignUpRequest
    ): Response<SignUpResult>

    @POST("api/user/login")
    suspend fun login(
        @Body data: LoginRequest
    ): Response<LoginResult>

    @POST("api/user/kakao/login")
    suspend fun kakaoLogin(
        @Header("Authorization") authorization: String,
        @Header("refreshToken") refreshToken: String
    ): Response<KakaoSignUpResult>

    @GET("api/version")
    suspend fun versionCheck(
        @Query("type") type: String,
        @Query("version") version: String
    ): Response<Boolean>

    @GET("api/email/auth/{email}")
    suspend fun requestEmailAuth(
        @Path("email") email: String
    ): Response<Unit>

    @POST("api/email/verify")
    suspend fun emailVerify(
        @Body data: EmailVerifyRequest
    ): Response<String>

}