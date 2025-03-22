package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import com.sooum.domain.usecase.meet.GetMeetDetailByIdUseCase
import com.sooum.domain.usecase.meet.UpdateMeetDetailScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailViewModel @Inject constructor(
    private val getMeetDetailByIdUseCase: GetMeetDetailByIdUseCase,
    private val updateMeetDetailScheduleUseCase: UpdateMeetDetailScheduleUseCase
) : ViewModel() {


    private var _meetDetail: MutableStateFlow<MeetDetail?> = MutableStateFlow(null)

    val meetDetail
        get() = _meetDetail.asStateFlow()

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
        complete :() ->Unit
    ) {
        viewModelScope.launch {
            _meetDetail.value?.let {
                updateMeetDetailScheduleUseCase(it.id, schedule)
                complete()
            }
        }
    }
}
