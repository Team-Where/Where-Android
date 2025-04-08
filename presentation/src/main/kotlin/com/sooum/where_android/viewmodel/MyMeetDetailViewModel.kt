package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.PlaceList
import com.sooum.domain.model.Schedule
import com.sooum.domain.model.ShareResult
import com.sooum.domain.usecase.meet.AddMeetScheduleUseCase
import com.sooum.domain.usecase.meet.ClearMeetUseCase
import com.sooum.domain.usecase.meet.ExitMeetUseCase
import com.sooum.domain.usecase.meet.GetMeetDetailByIdUseCase
import com.sooum.domain.usecase.meet.GetMeetInviteStatusUseCase
import com.sooum.domain.usecase.meet.GetMeetPlaceListUseCase
import com.sooum.domain.usecase.meet.UpdateMeetScheduleUseCase
import com.sooum.domain.usecase.place.AddPlaceUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailViewModel @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val getMeetDetailByIdUseCase: GetMeetDetailByIdUseCase,
    getMeetInviteStatusUseCase: GetMeetInviteStatusUseCase,
    getMeetPlaceListUseCase: GetMeetPlaceListUseCase,
    private val addMeetScheduleUseCase: AddMeetScheduleUseCase,
    private val updateMeetScheduleUseCase: UpdateMeetScheduleUseCase,
    private val exitMeetUseCase: ExitMeetUseCase,
    private val clearMeetUseCase: ClearMeetUseCase,
    private val addPlaceUseCase: AddPlaceUseCase,
) : ViewModel() {


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
            null,
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

    val userAndPlaceMap = inviteStatus
        .transform { list ->
            emit(list.filter { it.status })
        }
        .combine(placeMap) { invitedUser, placeMap ->
            val tempData = mutableListOf<PlaceList>()
            val myId = getLoginUserIdUseCase()!!
            placeMap.forEach { (userId, placeItemList) ->
                if (userId == myId) {
                    tempData.add(
                        PlaceList.ProfileHeader(
                            userId = userId,
                            userName = "나",
                            profileImage = null
                        )
                    )
                    placeItemList.forEach { place ->
                        tempData.add(
                            PlaceList.PostItem(
                                userId = userId,
                                place = place
                            )
                        )
                    }
                } else {
                    invitedUser.find { it.toId == userId }?.let { findUser ->
                        tempData.add(
                            PlaceList.ProfileHeader(
                                userId = userId,
                                userName = findUser.toName,
                                profileImage = findUser.toImage
                            )
                        )
                        placeItemList.forEach { place ->
                            tempData.add(
                                PlaceList.PostItem(
                                    userId = userId,
                                    place = place
                                )
                            )
                        }
                    }
                }
            }

            // 정렬 로직
            val grouped = tempData.groupBy { it.userId }
                .mapNotNull { (userId, items) ->
                    val header = items.find { it is PlaceList.ProfileHeader } as? PlaceList.ProfileHeader
                    val posts = items.filterIsInstance<PlaceList.PostItem>()
                    if (header != null) {
                        listOf(header) + posts
                    } else {
                        null // header 없는 유저는 제외하거나 필요 시 처리 가능
                    }
                }

            val sorted = grouped.sortedWith(
                compareBy<List<PlaceList>> { group ->
                    val uid = group.first().userId
                    if (uid == myId) Int.MIN_VALUE else uid
                }.thenBy { group ->
                    (group.first() as? PlaceList.ProfileHeader)?.userName ?: ""
                }
            ).flatten()

            sorted
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
                addPlaceUseCase(it.id, shareResult)
                complete()
            }
        }
    }

    /**
     * 모임 탈퇴(삭제 합니다)
     */
    fun exitMeet(
        complete: (ActionResult<Unit>) -> Unit
    ) {
        viewModelScope.launch {
            val userId = getLoginUserIdUseCase()!!
            meetDetail.value?.let { meetDetail ->
                val result = exitMeetUseCase(
                    meetId = meetDetail.id,
                    userId = userId
                )
                complete(result)
            }
        }
    }

    fun likeToggle(placeId:Int) {

    }

    override fun onCleared() {
        viewModelScope.launch {
            clearMeetUseCase()
        }
        super.onCleared()
    }
}

