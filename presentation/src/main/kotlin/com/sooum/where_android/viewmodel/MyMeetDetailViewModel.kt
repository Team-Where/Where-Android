package com.sooum.where_android.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.data.repository.MeetDetailRepositoryImpl
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlaceDelete
import com.sooum.domain.model.PlaceLike
import com.sooum.domain.model.PlaceList
import com.sooum.domain.model.PlaceStatus
import com.sooum.domain.model.Schedule
import com.sooum.domain.model.ShareResult
import com.sooum.domain.usecase.meet.AddMeetScheduleUseCase
import com.sooum.domain.usecase.meet.ClearMeetUseCase
import com.sooum.domain.usecase.meet.ExitMeetUseCase
import com.sooum.domain.usecase.meet.FinishMeetUseCase
import com.sooum.domain.usecase.meet.GetMeetDetailByIdUseCase
import com.sooum.domain.usecase.meet.GetMeetInviteStatusUseCase
import com.sooum.domain.usecase.meet.GetMeetPlaceListUseCase
import com.sooum.domain.usecase.meet.UpdateMeetCoverUseCase
import com.sooum.domain.usecase.meet.UpdateMeetDescriptionUseCase
import com.sooum.domain.usecase.meet.UpdateMeetScheduleUseCase
import com.sooum.domain.usecase.meet.UpdateMeetTitleUseCase
import com.sooum.domain.usecase.place.AddPlaceUseCase
import com.sooum.domain.usecase.place.TogglePlaceLikeUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    private val exitMeetUseCase: ExitMeetUseCase,
    private val finishMeetUseCase: FinishMeetUseCase,
) : ViewModel() {

    companion object {
        private const val FCM_CODE_PLACE_ADD = "101"
        private const val FCM_CODE_PLACE_DELETE = "103"
        private const val FCM_CODE_PLACE_STATUS_UPDATE = "104"
        private const val FCM_CODE_PLACE_LIKE_UPDATE = "105"
        private const val FCM_CODE_PLACE_PLACE_LIST = "106"
        private const val FCM_CODE_SCHEDULE_ADD_ = "201"
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

    val waitingFriendList = inviteStatus.transform { list ->
        val convertList = mutableListOf<InvitedFriend>()
        val filterData =
            list.filter { !it.status }.map { InvitedFriend(it.toId, it.toName, it.toImage) }
                .sortedBy { it.name }
        convertList.addAll(filterData)
        emit(convertList)
    }

    private val placeMap = getMeetPlaceListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyMap()
        )

    val placeCount = placeMap.transform {
        emit(it.flatMap { it.value }.size)
    }

    val userAndPlaceMap = invitedFriendList
        .combine(placeMap) { invitedFriendList, placeMap ->
            val tempData = mutableListOf<PlaceList>()
            val myId = getLoginUserIdUseCase()!!

            //헤더 먼저 추가 (ProfileHeader)
            invitedFriendList.forEach {
                tempData.add(
                    PlaceList.ProfileHeader(
                        userId = it.id,
                        userName = if (it.isMe) "나" else it.name,
                        profileImage = it.image
                    )
                )
            }

            //장소를 돌며 장소를 추가 (PostItem)
            placeMap.forEach { (userId, placeItemList) ->
                placeItemList.forEach { place ->
                    tempData.add(
                        PlaceList.PostItem(
                            userId = userId,
                            place = place
                        )
                    )
                }
            }
            val comparator = compareBy<PlaceList>(
                { if (it.userId == myId) 0 else 1 }, // myId 먼저
                { it.userId },
                { if (it is PlaceList.ProfileHeader) 0 else 1 } // Header 먼저
            )

            tempData.sortedWith(comparator)
        }

    val pickPlaceList = placeMap.transform {
        val filterList = it.values.flatten().toSet().filter { it.status == "Picked" }
        emit(filterList.sortedBy { it.likeCount })
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
        try {
            when (code) {
                FCM_CODE_PLACE_ADD -> {
                    val shareResult = Json.decodeFromString<Place>(data)
                    repositoryImpl.addPlaceToMeeting(
                        newPlace = shareResult,
                        id = shareResult.id
                    )
                }

                FCM_CODE_PLACE_STATUS_UPDATE -> {
                    val shareResult = Json.decodeFromString<PlaceStatus>(data)
                    repositoryImpl.updatePlaceStatusToPicked(
                        placeId = shareResult.placeId,
                        newStatus = shareResult.placeStatus
                    )
                }

                FCM_CODE_PLACE_DELETE -> {
                    val shareResult = Json.decodeFromString<PlaceDelete>(data)
                    repositoryImpl.deletePlaceFromMeeting(shareResult.id)
                }
                FCM_CODE_PLACE_LIKE_UPDATE -> {
                    val shareResult = Json.decodeFromString<PlaceLike>(data)
                    repositoryImpl.updatePlaceLike(
                        shareResult.placeId,
                        shareResult.likeCount
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("ViewModel", "JSON 파싱 실패: ${e.localizedMessage}")
        }
    }
}

