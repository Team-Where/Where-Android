package com.sooum.data.network.meet

import com.sooum.data.network.meet.request.DeleteMeetRequest
import com.sooum.data.network.meet.request.FinishMeetRequest
import com.sooum.data.network.meet.request.InviteMeetRequest
import com.sooum.data.network.meet.response.MeetListItemResponse
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetInviteStatus
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface MeetApi {

    @Multipart
    @POST("api/meeting")
    suspend fun addMeet(
        @Part("data") data: RequestBody,
        @Part imageFile: MultipartBody.Part
    ): Response<Meet>

    @Multipart
    @PUT("api/meeting")
    suspend fun editMeet(
        @Part("data") data: RequestBody,
        @Part imageFile: MultipartBody.Part?
    ): Response<Meet>

    @DELETE("api/meeting")
    suspend fun deleteMeet(
        @Body data: DeleteMeetRequest,
    ): Response<Any>

    @PUT("api/meeting/finish")
    suspend fun finishMeet(
        @Body data: FinishMeetRequest,
    ): Response<Any>

    @GET("api/meeting/participants/{id}")
    suspend fun getMeetInviteStatus(
        @Path("id") meetId: Int
    ): Response<List<MeetInviteStatus>>

    @GET("api/meeting/{id}")
    suspend fun getMeetList(
        @Path("id") userId: Int
    ): Response<List<MeetListItemResponse>>


    @POST("api/meeting/invite")
    suspend fun inviteMeet(
        @Body data: InviteMeetRequest
    ): Response<Any>

    @POST("api/meeting/invite/ok")
    suspend fun inviteMeetOk(
        @Body data: InviteMeetRequest
    ): Response<Meet>
}