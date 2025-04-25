package com.sooum.where_android.viewmodel.meetdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.core.FcmConst
import com.sooum.domain.model.CommentData
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.FcmMeetInviteStatus
import com.sooum.domain.model.MeetingId
import com.sooum.domain.model.PlaceDelete
import com.sooum.domain.model.PlaceLike
import com.sooum.domain.model.PlaceStatus
import com.sooum.domain.model.PlaceWithUsers
import com.sooum.domain.model.Schedule
import com.sooum.domain.usecase.comment.fcm.AddCommentFromFcmUseCase
import com.sooum.domain.usecase.comment.fcm.DeleteCommentFromFcmUseCase
import com.sooum.domain.usecase.comment.fcm.UpdateCommentFromFcmUseCase
import com.sooum.domain.usecase.meet.fcm.DeleteMeetScheduleFromFcmUseCase
import com.sooum.domain.usecase.meet.fcm.UpdateMeetStatusFromFcmUseCase
import com.sooum.domain.usecase.meet.fcm.UpdateScheduleFromFcmUseCase
import com.sooum.domain.usecase.place.fcm.AddNewPlaceUserFromFcmCase
import com.sooum.domain.usecase.place.fcm.DeletePlaceFromFcmUseCase
import com.sooum.domain.usecase.place.fcm.UpdatePlaceLikeFromFcmUseCase
import com.sooum.domain.usecase.place.fcm.UpdatePlaceStatusFromFcmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailFcmViewModel @Inject constructor(
    private val updateScheduleUseCase: UpdateScheduleFromFcmUseCase,
    private val deleteMeetScheduleUseCase: DeleteMeetScheduleFromFcmUseCase,
    private val addNewPlaceUseCase: AddNewPlaceUserFromFcmCase,
    private val deletePlaceUseCase: DeletePlaceFromFcmUseCase,
    private val updatePlaceLikeUseCase: UpdatePlaceLikeFromFcmUseCase,
    private val updatePlaceStatusUseCase: UpdatePlaceStatusFromFcmUseCase,
    private val addCommentUseCase: AddCommentFromFcmUseCase,
    private val updateCommentUseCase: UpdateCommentFromFcmUseCase,
    private val deleteCommentUseCase: DeleteCommentFromFcmUseCase,
    private val updateMeetStatusFromFcmUseCase: UpdateMeetStatusFromFcmUseCase
) : ViewModel() {

    fun updatePlaceFromFcm(code: String, data: String) {
        viewModelScope.launch {
            try {
                when (code) {
                    FcmConst.FCM_CODE_PLACE_ADD -> {
                        val shareResult = Json.decodeFromString<PlaceWithUsers>(data)
                        addNewPlaceUseCase(
                            shareResult.id,
                            shareResult
                        )
                    }

                    FcmConst.FCM_CODE_PLACE_DELETE -> {
                        val shareResult = Json.decodeFromString<PlaceDelete>(data)
                        deletePlaceUseCase(
                            shareResult.id
                        )
                    }

                    FcmConst.FCM_CODE_PLACE_STATUS_UPDATE -> {
                        val shareResult = Json.decodeFromString<PlaceStatus>(data)
                        updatePlaceStatusUseCase(
                            shareResult.placeId,
                            shareResult.placeStatus
                        )
                    }

                    FcmConst.FCM_CODE_PLACE_LIKE_UPDATE -> {
                        val shareResult = Json.decodeFromString<PlaceLike>(data)
                        updatePlaceLikeUseCase(
                            shareResult.placeId,
                            shareResult.likeCount
                        )
                    }

                    FcmConst.FCM_CODE_SCHEDULE_ADD, FcmConst.FCM_CODE_SCHEDULE_PUT -> {
                        val schedule = Json.decodeFromString<Schedule>(data)
                        updateScheduleUseCase(
                            schedule.meetId,
                            schedule.date,
                            schedule.time
                        )
                    }

                    FcmConst.FCM_CODE_SCHEDULE_DELETE -> {
                        val shareResult = Json.decodeFromString<MeetingId>(data)
                        deleteMeetScheduleUseCase(
                            shareResult.meetingId
                        )
                    }

                    FcmConst.FCM_CODE_COMMENT_ADD -> {
                        val shareResult = Json.decodeFromString<CommentListItem>(data)
                        addCommentUseCase(
                            shareResult.placeId,
                            shareResult
                        )

                    }

                    FcmConst.FCM_CODE_COMMENT_PUT -> {
                        val shareResult = Json.decodeFromString<CommentData.Detail>(data)
                        updateCommentUseCase(
                            shareResult.id,
                            shareResult.description
                        )
                    }

                    FcmConst.FCM_CODE_COMMENT_DELETE -> {
                        val shareResult = Json.decodeFromString<CommentData.IdOnly>(data)
                        deleteCommentUseCase(
                            shareResult.id
                        )
                    }
                    FcmConst.FCM_CODE_MEET_ACCEPT -> {
                        val shareResult = Json.decodeFromString<FcmMeetInviteStatus>(data)
                        updateMeetStatusFromFcmUseCase(
                            shareResult.userId
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "JSON 파싱 실패: ${e.localizedMessage}")
            }
        }
    }
}

