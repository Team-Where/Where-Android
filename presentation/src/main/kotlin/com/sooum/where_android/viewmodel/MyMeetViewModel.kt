package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import com.sooum.domain.usecase.meet.LoadMeetDetailListUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  MyMeetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val loadMeetDetailListUseCase: LoadMeetDetailListUseCase,
    getMeetDetailListUseCase: GetMeetDetailListUseCase
) : ViewModel() {

    val meetDetailList = getMeetDetailListUseCase().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            getLoginUserIdUseCase()?.let { id ->
                loadMeetDetailListUseCase(id)
            }
        }
    }
}