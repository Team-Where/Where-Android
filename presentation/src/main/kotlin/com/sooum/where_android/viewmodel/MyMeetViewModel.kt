package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.usecase.GetUserUseCase
import com.sooum.domain.usecase.meet.GetMeetDetailListGroupByYearUseCase
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyMeetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMeetDetailListUseCase: GetMeetDetailListUseCase
) : ViewModel() {

    val meetDetailList = getMeetDetailListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )
}