package com.sooum.data.network.user

import com.sooum.data.network.user.request.EditNicknameRequest
import com.sooum.data.network.user.response.AddProfileResponse
import com.sooum.data.network.user.response.EditProfileResponse
import com.sooum.data.network.user.response.MyPageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApi {

    @Headers("Cache-Control: max-age=60")
    @GET("api/user/mypage/{userId}")
    suspend fun getMyPage(
        @Path("userId") userId: Int
    ): Response<MyPageResponse>

    @Multipart
    @POST("api/user/{userId}/upload")
    suspend fun addProfileImage(
        @Path("userId") userId: Int,
        @Part imageFile: MultipartBody.Part
    ): Response<AddProfileResponse>

    @Multipart
    @PUT("api/user/{userId}/edit")
    suspend fun editProfileImage(
        @Path("userId") userId: Int,
        @Part imageFile: MultipartBody.Part
    ): Response<EditProfileResponse>

    @DELETE("api/user/{userId}/delete")
    suspend fun deleteProfileImage(
        @Path("userId") userId: Int,
    ): Response<Any>

    @PUT("api/user/{userId}/nickname")
    suspend fun editNickName(
        @Path("userId") userId: Int,
        @Body data: EditNicknameRequest
    ): Response<Any>
}