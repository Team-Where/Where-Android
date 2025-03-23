package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.Meet
import com.sooum.domain.model.Schedule
import com.sooum.domain.model.ShareResult
import com.sooum.domain.usecase.meet.GetMeetDetailByIdUseCase
import com.sooum.domain.usecase.meet.UpdateMeetDetailScheduleUseCase
import com.sooum.domain.usecase.place.AddPlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMeetDetailViewModel @Inject constructor(
    private val getMeetDetailByIdUseCase: GetMeetDetailByIdUseCase,
    private val updateMeetDetailScheduleUseCase: UpdateMeetDetailScheduleUseCase,
    private val addPlaceUseCase: AddPlaceUseCase,
) : ViewModel() {


    private var _meetDetail: MutableStateFlow<Meet?> = MutableStateFlow(null)

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
        complete: () -> Unit
    ) {
        viewModelScope.launch {
            _meetDetail.value?.let {
                updateMeetDetailScheduleUseCase(it.id, schedule)
                complete()
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
}
