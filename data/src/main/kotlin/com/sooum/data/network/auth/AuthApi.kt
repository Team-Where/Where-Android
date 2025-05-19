package com.sooum.data.network.auth

import com.sooum.data.network.auth.request.CheckEmailRequest
import com.sooum.data.network.auth.request.LoginRequest
import com.sooum.data.network.auth.request.NameOnlyRequest
import com.sooum.data.network.auth.request.SignUpRequest
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.LoginResult
import com.sooum.domain.model.PostProfileResult
import com.sooum.domain.model.SignUpResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthApi {
    @POST("api/user/signup")
    suspend fun signUp(
        @Body data: SignUpRequest
    ): Response<SignUpResult>

    @POST("api/user/login")
    suspend fun login(
        @Body data: LoginRequest
    ): Response<LoginResult>

    @POST("api/user/checkEmail")
    suspend fun checkEmail(
        @Body data: CheckEmailRequest
    ): Response<Any>

    @POST("api/user/kakao/login")
    suspend fun kakaoLogin(
        @Header("Authorization") authorization: String,
        @Header("refreshToken") refreshToken: String
    ): Response<KakaoSignUpResult>

    @PUT("api/user/{userId}/nickname")
    suspend fun putNickName(
        @Path("userId") userId: Int,
        @Body data: NameOnlyRequest
    ) : Response<Unit>

    @Multipart
    @POST("api/user/{userId}/upload")
    suspend fun postProfileImage(
        @Path("userId") userId: Int,
        @Part file: MultipartBody.Part?
    ): Response<PostProfileResult>
}