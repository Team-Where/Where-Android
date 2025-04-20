package com.sooum.where_android.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.repository.MeetDetailRepositoryImpl
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetingId
import com.sooum.domain.model.PLACE_STATE_PICK
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlaceDelete
import com.sooum.domain.model.PlaceLike
import com.sooum.domain.model.PlaceRank
import com.sooum.domain.model.PlaceStatus
import com.sooum.domain.model.PlaceWithUsers
import com.sooum.domain.model.Schedule
import com.sooum.domain.model.ShareResult
import com.sooum.domain.usecase.meet.AddMeetScheduleUseCase
import com.sooum.domain.usecase.meet.ClearMeetUseCase
import com.sooum.domain.usecase.meet.DeleteMeetScheduleUseCase
import com.sooum.domain.usecase.meet.ExitMeetUseCase
import com.sooum.domain.usecase.meet.FinishMeetUseCase
import com.sooum.domain.usecase.meet.GetMeetDetailByIdUseCase
import com.sooum.domain.usecase.meet.GetMeetInviteStatusUseCase
import com.sooum.domain.usecase.meet.GetMeetPlaceListUseCase
import com.sooum.domain.usecase.meet.UpdateMeetCoverUseCase
import com.sooum.domain.usecase.meet.UpdateMeetDescriptionUseCase
import com.sooum.domain.usecase.meet.UpdateMeetScheduleUseCase
import com.sooum.domain.usecase.meet.UpdateMeetTitleUseCase
import com.sooum.domain.usecase.meet.UpdateScheduleUseCase
import com.sooum.domain.usecase.place.AddNewPlaceUserCase
import com.sooum.domain.usecase.place.AddPlaceUseCase
import com.sooum.domain.usecase.place.DeletePlaceUseCase
import com.sooum.domain.usecase.place.TogglePlaceLikeUseCase
import com.sooum.domain.usecase.place.UpdatePlaceLikeUseCase
import com.sooum.domain.usecase.place.UpdatePlaceStatusUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailViewModel @Inject constructor(
    private val repositoryImpl: MeetDetailRepositoryImpl,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val getMeetDetailByIdUseCase: GetMeetDetailByIdUseCase,
    getMeetInviteStatusUseCase: GetMeetInviteStatusUseCase,
    getMeetPlaceListUseCase: GetMeetPlaceListUseCase,
    private val addMeetScheduleUseCase: AddMeetScheduleUseCase,
    private val addPlaceUseCase: AddPlaceUseCase,
    private val updateMeetScheduleUseCase: UpdateMeetScheduleUseCase,
    private val updateMeetTitleUseCase: UpdateMeetTitleUseCase,
    private val updateMeetDescriptionUseCase: UpdateMeetDescriptionUseCase,
    private val updateMeetCoverUseCase: UpdateMeetCoverUseCase,
    private val togglePlaceLikeUseCase: TogglePlaceLikeUseCase,
    private val clearMeetUseCase: ClearMeetUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val deleteMeetScheduleUseCase: DeleteMeetScheduleUseCase,
    private val addNewPlaceUseCase: AddNewPlaceUserCase,
    private val deletePlaceUseCase: DeletePlaceUseCase,
    private val updatePlaceLikeUseCase: UpdatePlaceLikeUseCase,
    private val updatePlaceStatusUseCase: UpdatePlaceStatusUseCase,
    private val exitMeetUseCase: ExitMeetUseCase,
    private val finishMeetUseCase: FinishMeetUseCase,
) : ViewModel() {

    companion object {
        private const val FCM_CODE_PLACE_ADD = "101"
        private const val FCM_CODE_PLACE_DELETE = "103"
        private const val FCM_CODE_PLACE_STATUS_UPDATE = "104"
        private const val FCM_CODE_PLACE_LIKE_UPDATE = "105"
        private const val FCM_CODE_PLACE_PLACE_LIST = "106"
        private const val FCM_CODE_SCHEDULE_ADD = "201"
        private const val FCM_CODE_SCHEDULE_PUT = "202"
        private const val FCM_CODE_SCHEDULE_DELETE = "203"
        private const val FCM_CODE_COMMENT_ADD = "301"
        private const val FCM_CODE_COMMENT_PUT = "302"
        private const val FCM_CODE_COMMENT_DELETE = "303"
        private const val FCM_CODE_MEET_ACCEPT = "404"
    }

    private var _meetDetail: MutableStateFlow<MeetDetail?> = MutableStateFlow(null)

    val meetDetail
        get() = _meetDetail.asStateFlow()

    private val inviteStatus = getMeetInviteStatusUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    //초대된 친구 목록(추가로 나를 넣는다)
    val invitedFriendList = inviteStatus.transform { list ->
        val convertList = mutableListOf<InvitedFriend>()
        val myData = InvitedFriend(
            getLoginUserIdUseCase()!!,
            "나",
            null, //TODO 내 프로필 가져오기
            isMe = true
        )
        convertList.add(myData)
        val filterData =
            list.filter { it.status }.map { InvitedFriend(it.toId, it.toName, it.toImage) }
                .sortedBy { it.name }
        convertList.addAll(filterData)
        emit(convertList)
    }

    //초대 대기중인 친구 목록
    val waitingFriendList = inviteStatus.transform { list ->
        val convertList = mutableListOf<InvitedFriend>()
        val filterData =
            list.filter { !it.status }.map { InvitedFriend(it.toId, it.toName, it.toImage) }
                .sortedBy { it.name }
        convertList.addAll(filterData)
        emit(convertList)
    }

    //모임 상세에 로드된 place 목록
    val placeList = getMeetPlaceListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    //plceMap에있는 총 장소 수
    val placeCount = placeList.transform {
        emit(it.size)
    }

    //pick된 장소
    val pickPlaceList = placeList.transform { places ->
        val filterList = places.filter { it.status == PLACE_STATE_PICK }
        emit(filterList.sortedBy { it.likeCount })
    }

    //상위 3개의 장소
    val bestPlaceList = placeList.transform { places ->
        val sorted = places.sortedByDescending { it.likeCount }

        val result = mutableListOf<PlaceRank>()
        var currentRank = 0
        var lastLikeCount: Int? = null

        val groupedByRank = linkedMapOf<Int, MutableList<PlaceWithUsers>>()

        //3위까지 찾기 시작
        for (place in sorted) {
            if (place.likeCount != lastLikeCount) {
                currentRank += 1
                lastLikeCount = place.likeCount
            }

            if (currentRank > 3) break

            groupedByRank.getOrPut(currentRank) { mutableListOf() }.add(place)
        }

        //rank별 아이템 생성
        for ((rank, placesInRank) in groupedByRank) {
            result.add(PlaceRank.RankHeader(rank))
            placesInRank.forEach { place ->
                result.add(PlaceRank.PostItem(rank, place))
            }
        }

        emit(result)
    }

    fun loadData(
        meetDetailId: Int
    ) {
        viewModelScope.launch {
            getMeetDetailByIdUseCase(meetDetailId).collect {
                _meetDetail.value = it
            }
        }
    }

    /**
     * 새로운 스케줄로 변경
     */
    fun newSchedule(
        schedule: Schedule,
        complete: (ActionResult<Schedule>) -> Unit
    ) {
        viewModelScope.launch {
            _meetDetail.value?.let { meetDetail ->
                val result = if (meetDetail.schedule == null) {
                    addMeetScheduleUseCase(meetDetail.id, schedule)
                } else {
                    updateMeetScheduleUseCase(meetDetail.id, schedule)
                }
                complete(result)
            }
        }
    }

    fun addPlace(
        shareResult: ShareResult,
        complete: () -> Unit
    ) {
        viewModelScope.launch {
            _meetDetail.value?.let {
                addPlaceUseCase(it.id, getLoginUserIdUseCase()!!, shareResult)
                complete()
            }
        }
    }

    private inline fun findMeetDetailOrFail(
        doAction: (MeetDetail) -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        meetDetail.value?.let { meetDetail ->
            doAction(meetDetail)
        } ?: run {
            onFail("존재 하지 않는 id")
        }
    }

    /**
     * 모임 탈퇴(삭제 합니다)
     */
    fun exitMeet(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            val userId = getLoginUserIdUseCase()!!
            findMeetDetailOrFail(
                doAction = { meetDetail ->
                    val result = exitMeetUseCase(
                        meetId = meetDetail.id,
                        userId = userId
                    )
                    when (result) {
                        is ActionResult.Success -> {
                            onSuccess()
                        }

                        is ActionResult.Fail -> {
                            onFail(result.msg)
                        }
                    }
                },
                onFail = onFail
            )
        }
    }

    /**
     * 모임 종료
     */
    fun finishMeet(
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            findMeetDetailOrFail(
                doAction = { meetDetail ->
                    val result = finishMeetUseCase(
                        meetId = meetDetail.id,
                    )
                    when (result) {
                        is ActionResult.Success -> {
                            onSuccess()
                        }

                        is ActionResult.Fail -> {
                            onFail(result.msg)
                        }
                    }
                },
                onFail = onFail
            )
        }
    }

    fun likeToggle(
        placeId: Int,
        onSuccess: () -> Unit,
        onFail: (msg: String) -> Unit
    ) {
        viewModelScope.launch {
            val result = togglePlaceLikeUseCase(placeId)
            when (result) {
                is ActionResult.Success -> {
                    onSuccess()
                }

                is ActionResult.Fail -> {
                    onFail(result.msg)
                }
            }
        }
    }

    fun updateTitle(
        newTitle: String,
        onSuccess: () -> Unit,
        onFail: (msg:String) -> Unit
    ) {
        viewModelScope.launch {
            findMeetDetailOrFail(
                doAction = { meetDetail ->
                    val result = updateMeetTitleUseCase(
                        id = meetDetail.id,
                        title = newTitle
                    )
                    when (result) {
                        is ActionResult.Success -> {
                            onSuccess()
                        }

                        is ActionResult.Fail -> {
                            onFail(result.msg)
                        }
                    }
                },
                onFail = onFail
            )
        }
    }

    fun updateDescription(
        newDescription: String,
        onSuccess: () -> Unit,
        onFail: (msg:String) -> Unit
    ) {
        viewModelScope.launch {
            findMeetDetailOrFail(
                doAction = { meetDetail ->
                    val result = updateMeetDescriptionUseCase(
                        id = meetDetail.id,
                        description = newDescription
                    )
                    when (result) {
                        is ActionResult.Success -> {
                            onSuccess()
                        }

                        is ActionResult.Fail -> {
                            onFail(result.msg)
                        }
                    }
                },
                onFail = onFail
            )
        }
    }
    fun updateImage(
        image: ImageAddType,
        onSuccess: () -> Unit,
        onFail: (msg:String) -> Unit
    ) {
        viewModelScope.launch {
            findMeetDetailOrFail(
                doAction = { meetDetail ->
                    val result = updateMeetCoverUseCase(
                        id = meetDetail.id,
                        imageFile = image
                    )
                    when (result) {
                        is ActionResult.Success -> {
                            onSuccess()
                        }

                        is ActionResult.Fail -> {
                            onFail(result.msg)
                        }
                    }
                },
                onFail = onFail
            )
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            clearMeetUseCase()
        }
        super.onCleared()
    }

    fun updatePlaceFromFcm(code: String, data: String) {
        viewModelScope.launch {
            try {
                when (code) {
                    FCM_CODE_PLACE_ADD -> {
                        val shareResult = Json.decodeFromString<Place>(data)
                        addNewPlaceUseCase(
                            shareResult.id,
                            shareResult
                        )
                    }

                    FCM_CODE_PLACE_DELETE -> {
                        val shareResult = Json.decodeFromString<PlaceDelete>(data)
                        deletePlaceUseCase(
                            shareResult.id
                        )
                    }

                    FCM_CODE_PLACE_STATUS_UPDATE -> {
                        val shareResult = Json.decodeFromString<PlaceStatus>(data)
                        updatePlaceStatusUseCase(
                            shareResult.placeId,
                            shareResult.placeStatus
                        )
                    }

                    FCM_CODE_PLACE_LIKE_UPDATE -> {
                        val shareResult = Json.decodeFromString<PlaceLike>(data)
                        updatePlaceLikeUseCase(
                            shareResult.placeId,
                            shareResult.likeCount
                        )
                    }

                    FCM_CODE_SCHEDULE_ADD, FCM_CODE_SCHEDULE_PUT -> {
                        val schedule = Json.decodeFromString<Schedule>(data)
                        updateScheduleUseCase(
                            schedule.meetId,
                            schedule.date,
                            schedule.time
                        )
                    }
                    FCM_CODE_SCHEDULE_DELETE -> {
                        val shareResult = Json.decodeFromString<MeetingId>(data)
                        deleteMeetScheduleUseCase(
                            shareResult.meetingId
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "JSON 파싱 실패: ${e.localizedMessage}")
            }
        }
    }
}

