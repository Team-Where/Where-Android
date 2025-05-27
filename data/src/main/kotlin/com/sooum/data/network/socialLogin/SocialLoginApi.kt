package com.sooum.data.network.socialLogin

import com.sooum.data.network.auth.request.NameOnlyRequest
import com.sooum.domain.model.KakaoSignUpResult
import com.sooum.domain.model.PostProfileResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface SocialLoginApi {

    @POST("api/user/kakao/login")
    suspend fun kakaoLogin(
        @Header("Authorization") authorization: String,
        @Header("refreshToken") refreshToken: String
    ): Response<KakaoSignUpResult>

    @POST("api/user/naver/login")
    suspend fun naverLogin(
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