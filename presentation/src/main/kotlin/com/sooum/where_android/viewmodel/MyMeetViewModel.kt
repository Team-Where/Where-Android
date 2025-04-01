package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MyMeetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMeetDetailListUseCase: GetMeetDetailListUseCase
) : ViewModel() {

    private var _meetDetailList = MutableStateFlow(emptyList<MeetDetail>())

    val meetDetailList
        get() = _meetDetailList
}