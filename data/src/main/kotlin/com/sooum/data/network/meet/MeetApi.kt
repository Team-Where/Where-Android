package com.sooum.data.network.meet

import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import javax.inject.Inject

interface MeetApi {

    @Multipart
    @POST("api/meeting")
    suspend fun addMeet(
        @Part("data") data: RequestBody,
        @Part imageFile: MultipartBody.Part?
    ): Response<Meet>


    @Multipart
    @PUT("api/meeting")
    suspend fun editMeet(
        @Part("data") data: RequestBody,
        @Part imageFile: MultipartBody.Part?
    ): Response<Meet>

    @Multipart
    @DELETE("api/meeting")
    suspend fun deleteMeet(
        @Part("data") data: RequestBody,
        @Part imageFile: MultipartBody.Part?
    ): Response<Meet>
}