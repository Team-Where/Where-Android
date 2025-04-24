package com.sooum.where_android.viewmodel.meetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import com.sooum.domain.usecase.meet.detail.ClearMeetUseCase
import com.sooum.domain.usecase.meet.detail.ExitMeetUseCase
import com.sooum.domain.usecase.meet.detail.FinishMeetUseCase
import com.sooum.domain.usecase.meet.detail.GetMeetDetailByIdUseCase
import com.sooum.domain.usecase.meet.detail.UpdateMeetCoverUseCase
import com.sooum.domain.usecase.meet.detail.UpdateMeetDescriptionUseCase
import com.sooum.domain.usecase.meet.detail.UpdateMeetTitleUseCase
import com.sooum.domain.usecase.meet.invite.GetMeetInviteStatusUseCase
import com.sooum.domain.usecase.meet.schedule.AddMeetScheduleUseCase
import com.sooum.domain.usecase.meet.schedule.UpdateMeetScheduleUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailViewModel @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val getMeetDetailByIdUseCase: GetMeetDetailByIdUseCase,
    getMeetInviteStatusUseCase: GetMeetInviteStatusUseCase,
    private val addMeetScheduleUseCase: AddMeetScheduleUseCase,
    private val updateMeetScheduleUseCase: UpdateMeetScheduleUseCase,
    private val updateMeetTitleUseCase: UpdateMeetTitleUseCase,
    private val updateMeetDescriptionUseCase: UpdateMeetDescriptionUseCase,
    private val updateMeetCoverUseCase: UpdateMeetCoverUseCase,
    private val clearMeetUseCase: ClearMeetUseCase,
    private val exitMeetUseCase: ExitMeetUseCase,
    private val finishMeetUseCase: FinishMeetUseCase,
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
}

