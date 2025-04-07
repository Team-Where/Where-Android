package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.MeetInviteStatus
import com.sooum.domain.model.Schedule
import com.sooum.domain.model.ShareResult
import com.sooum.domain.usecase.meet.AddMeetScheduleUseCase
import com.sooum.domain.usecase.meet.ClearMeetUseCase
import com.sooum.domain.usecase.meet.ExitMeetUseCase
import com.sooum.domain.usecase.meet.GetMeetDetailByIdUseCase
import com.sooum.domain.usecase.meet.GetMeetInviteStatusUseCase
import com.sooum.domain.usecase.meet.UpdateMeetScheduleUseCase
import com.sooum.domain.usecase.place.AddPlaceUseCase
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
    private val exitMeetUseCase: ExitMeetUseCase,
    private val clearMeetUseCase: ClearMeetUseCase,
    private val addPlaceUseCase: AddPlaceUseCase,
) : ViewModel() {


    private var _meetDetail: MutableStateFlow<MeetDetail?> = MutableStateFlow(null)

    val meetDetail
        get() = _meetDetail.asStateFlow()

    val inviteStatus = getMeetInviteStatusUseCase()
        .transform {
            emit(
                listOf(
                    MeetInviteStatus(
                        1,
                        "테스트유저",
                        2,
                        "Dummy1",
                        true,
                        "https://www.kasandbox.org/programming-images/avatars/leaf-blue.png",
                    ),
                    MeetInviteStatus(
                        1,
                        "테스트유저",
                        3,
                        "Dummy2",
                        false,
                        "https://www.kasandbox.org/programming-images/avatars/old-spice-man.png",
                    )
                )
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

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

    override fun onCleared() {
        viewModelScope.launch {
            clearMeetUseCase()
        }
        super.onCleared()
    }
}

