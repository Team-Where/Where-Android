package com.sooum.data.datasource

import android.util.Log
import com.sooum.data.network.comment.CommentApi
import com.sooum.data.network.comment.request.AddCommentRequest
import com.sooum.data.network.comment.request.DeleteCommentRequest
import com.sooum.data.network.comment.request.EditCommentRequest
import com.sooum.data.network.createPart
import com.sooum.data.network.meet.MeetApi
import com.sooum.data.network.meet.request.AddMeetRequest
import com.sooum.data.network.meet.request.DeleteMeetRequest
import com.sooum.data.network.meet.request.EditMeetRequest
import com.sooum.data.network.meet.request.FinishMeetRequest
import com.sooum.data.network.meet.request.InviteMeetOkLinkRequest
import com.sooum.data.network.meet.request.InviteMeetRequest
import com.sooum.data.network.place.PlaceApi
import com.sooum.data.network.place.request.AddPlaceRequest
import com.sooum.data.network.place.request.DeletePlaceRequest
import com.sooum.data.network.place.request.LikePlaceRequest
import com.sooum.data.network.place.request.PickPlaceRequest
import com.sooum.data.network.safeFlow
import com.sooum.data.network.schedule.ScheduleApi
import com.sooum.data.network.schedule.request.AddScheduleRequest
import com.sooum.data.network.schedule.request.DeleteScheduleRequest
import com.sooum.data.network.schedule.request.EditScheduleRequest
import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.model.ApiResult
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.CommentSimple
import com.sooum.domain.model.EditMeet
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlacePickStatus
import com.sooum.domain.model.PlaceWithUsers
import com.sooum.domain.model.Schedule
import com.sooum.domain.model.SimpleMeet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class MeetRemoteDataSourceImpl @Inject constructor(
    private val meetApi: MeetApi,
    private val placeApi: PlaceApi,
    private val commentApi: CommentApi,
    private val scheduleApi: ScheduleApi
) : MeetRemoteDataSource {

    private val json = Json {
        encodeDefaults = true
    }

    override suspend fun addMeet(
        title: String,
        fromId: Int,
        description: String,
        participants: List<Int>,
        imageFile: File?
    ): Flow<ApiResult<Meet>> {

        val request = AddMeetRequest(
            title,
            fromId,
            description,
            participants
        )

        val dataPart =
            json.encodeToString(request).toRequestBody("application/json".toMediaTypeOrNull())

        return safeFlow { meetApi.addMeet(dataPart, imageFile.createPart()) }
    }

    override suspend fun editMeet(
        id: Int,
        userId: Int,
        title: String?,
        description: String?,
        imageFile: File?
    ): Flow<ApiResult<EditMeet>> {
        val request = EditMeetRequest(
            id,
            userId,
            title,
            description,
        )
        val dataPart =
            json.encodeToString(request).toRequestBody("application/json".toMediaTypeOrNull())

        return safeFlow { meetApi.editMeet(dataPart, imageFile.createPart()) }
    }

    override suspend fun deleteCover(id: Int): Flow<ApiResult<String>> {
        return safeFlow { meetApi.deleteCover(id) }
    }

    override suspend fun deleteMeet(
        meetId: Int,
        userId: Int
    ): Flow<ApiResult<String>> {
        val request = DeleteMeetRequest(
            meetId = meetId,
            userId = userId
        )
        return safeFlow { meetApi.deleteMeet(request) }
    }

    override suspend fun finishMeet(meetId: Int, userId: Int): Flow<ApiResult<Any>> {
        val request = FinishMeetRequest(
            id = meetId,
            userId = userId
        )
        return safeFlow { meetApi.finishMeet(request) }
    }

    override suspend fun getMeetInviteStatus(meetId: Int): Flow<ApiResult<List<MeetInviteStatus>>> {
        return safeFlow { meetApi.getMeetInviteStatus(meetId) }
    }

    override suspend fun getMeetList(userId: Int): Flow<List<MeetDetail>> = flow {
        val apiResult = safeFlow { meetApi.getMeetList(userId) }.first()
        if (apiResult is ApiResult.Success) {
            emit(apiResult.data.map { it.toMeetDetail() })
        } else {
            emit(emptyList())
        }
    }

    override suspend fun inviteMeet(
        meetId: Int,
        fromUserId: Int,
        toUserId: Int
    ): Flow<ApiResult<String>> {
        val request = InviteMeetRequest(
            meetId = meetId,
            fromId = fromUserId,
            toId = toUserId
        )
        return safeFlow { meetApi.inviteMeet(request) }
    }

    override suspend fun getMeetInvite(link: String): Flow<ApiResult<SimpleMeet>> {
        return safeFlow { meetApi.getMeetInvite(link) }
    }

    override suspend fun addMeetPlace(
        meetId: Int,
        userId: Int,
        name: String,
        address: String,
    ): Flow<ApiResult<Place>> {
        val request = AddPlaceRequest(
            meetId,
            userId,
            name,
            address,
        )
        return safeFlow { placeApi.addPlace(request) }
    }

    override suspend fun getMeetPlaceList(
        meetId: Int,
        userId: Int
    ): Flow<ApiResult<List<PlaceWithUsers>>> {
        return safeFlow { placeApi.getPlaceList(meetId, userId) }
    }

    override suspend fun deleteMeetPlace(placeId: Int, userId: Int): Flow<ApiResult<Any>> {
        val request = DeletePlaceRequest(
            placeId = placeId,
            userId = userId
        )
        return safeFlow { placeApi.deletePlace(request) }
    }

    override suspend fun pickPlace(placeId: Int, userId: Int): Flow<ApiResult<PlacePickStatus>> {
        val request = PickPlaceRequest(
            placeId = placeId,
            userId = userId
        )
        return safeFlow { placeApi.pickPlace(request) }
    }

    override suspend fun likePlace(placeId: Int, userId: Int): Flow<ApiResult<PlacePickStatus>> {
        val request = LikePlaceRequest(
            placeId,
            userId
        )
        return safeFlow { placeApi.likePlace(request) }
    }

    override suspend fun addComment(
        placeId: Int,
        userId: Int,
        description: String
    ): Flow<ApiResult<CommentSimple>> {
        val request = AddCommentRequest(
            placeId = placeId,
            userId = userId,
            description = description
        )
        return safeFlow { commentApi.addPlaceComment(request) }
    }

    override suspend fun editComment(
        commentId: Int,
        userId: Int,
        description: String
    ): Flow<ApiResult<CommentSimple>> {
        val request = EditCommentRequest(
            commentId = commentId,
            userId = userId,
            description = description
        )
        return safeFlow { commentApi.editPlaceComment(request) }
    }

    override suspend fun deleteComment(commentId: Int, userId: Int): Flow<ApiResult<Any>> {
        val request = DeleteCommentRequest(
            commentId = commentId,
            userId = userId,
        )
        return safeFlow { commentApi.deletePlaceComment(request) }
    }

    override suspend fun getPlaceCommentList(
        placeId: Int,
        userId: Int
    ): Flow<ApiResult<List<CommentListItem>>> {
        return safeFlow { commentApi.getPlaceCommentList(placeId, userId) }
    }


    override suspend fun addSchedule(
        meetId: Int,
        userId: Int,
        date: String,
        time: String
    ): Flow<ApiResult<Schedule>> {
        getSchedule(meetId).first().also {
            Log.d("JWH", it.toString())
        }
        val request = AddScheduleRequest(
            meetId = meetId,
            userId = userId,
            date = date,
            time = time
        )
        return safeFlow { scheduleApi.addSchedule(request) }
    }

    override suspend fun getSchedule(meetingId: Int): Flow<ApiResult<Schedule>> {
        return safeFlow { scheduleApi.getSchedule(meetingId) }
    }

    override suspend fun editSchedule(
        meetId: Int,
        userId: Int,
        date: String?,
        time: String?
    ): Flow<ApiResult<Schedule>> {
        val request = EditScheduleRequest(
            meetId = meetId,
            userId = userId,
            date = date,
            time = time
        )
        return safeFlow { scheduleApi.editSchedule(request) }
    }

    override suspend fun deleteSchedule(meetId: Int): Flow<ApiResult<Any>> {
        val request = DeleteScheduleRequest(
            meetId = meetId
        )
        return safeFlow { scheduleApi.deleteSchedule(request) }
    }

    override suspend fun inviteOkFromLink(userId: Int, code: String): Flow<ApiResult<MeetDetail>> {
        val request = InviteMeetOkLinkRequest(
            userId = userId,
            link = code
        )
        return safeFlow { meetApi.inviteMeetOkLink(request) }.transform { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    emit(ApiResult.Success(apiResult.data.toMeetDetail()))
                }

                is ApiResult.SuccessEmpty -> {
                    emit(ApiResult.SuccessEmpty)
                }

                is ApiResult.Fail.Error -> {
                    emit(ApiResult.Fail.Error(apiResult.code, apiResult.message))
                }

                is ApiResult.Fail.Exception -> {
                    emit(ApiResult.Fail.Exception(apiResult.e))
                }
            }
        }
    }
}