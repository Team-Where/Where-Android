package com.sooum.data.network.meet

import com.sooum.data.network.meet.request.DeleteMeetRequest
import com.sooum.data.network.meet.request.FinishMeetRequest
import com.sooum.data.network.meet.request.InviteMeetOkLinkRequest
import com.sooum.data.network.meet.request.InviteMeetRequest
import com.sooum.data.network.meet.response.MeetListItemResponse
import com.sooum.domain.model.EditMeet
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.SimpleMeet
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Headers
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
        @Part imageFile: MultipartBody.Part
    ): Response<EditMeet>

    @DELETE("api/meeting/image/{id}")
    suspend fun deleteCover(
        @Path("id") id: Int
    ): Response<String>

    @HTTP(method = "DELETE", path = "api/meeting", hasBody = true)
    suspend fun deleteMeet(
        @Body data: DeleteMeetRequest,
    ): Response<String>

    @PUT("api/meeting/finish")
    suspend fun finishMeet(
        @Body data: FinishMeetRequest,
    ): Response<String>

    @Headers("Cache-Control: max-age=60")
    @GET("api/meeting/participant/{id}")
    suspend fun getMeetInviteStatus(
        @Path("id") meetId: Int
    ): Response<List<MeetInviteStatus>>

    @Headers("Cache-Control: max-age=60")
    @GET("api/meeting/{id}")
    suspend fun getMeetList(
        @Path("id") userId: Int
    ): Response<List<MeetListItemResponse>>

    @Headers("Cache-Control: max-age=60")
    @GET("api/meeting/invite/{link}")
    suspend fun getMeetInvite(
        @Path("link") link :String
    ): Response<SimpleMeet>

    @POST("api/meeting/invite")
    suspend fun inviteMeet(
        @Body data: InviteMeetRequest
    ): Response<Any>

    @POST("api/meeting/invite/ok")
    suspend fun inviteMeetOk(
        @Body data: InviteMeetRequest
    ): Response<MeetListItemResponse>

    @POST("api/meeting/invite/ok/link")
    suspend fun inviteMeetOkLink(
        @Body data: InviteMeetOkLinkRequest
    ): Response<MeetListItemResponse>
}