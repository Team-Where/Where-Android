package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import com.sooum.domain.usecase.user.GetLoginUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMeetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val getMeetDetailListUseCase: GetMeetDetailListUseCase
) : ViewModel() {

    private var _meetDetailList = MutableStateFlow(emptyList<MeetDetail>())

    val meetDetailList
        get() = _meetDetailList

    init {
        viewModelScope.launch {
            getLoginUserIdUseCase()?.let {id ->
                getMeetDetailListUseCase(id).collect { list ->
                    _meetDetailList.value = list
                }
            }
        }
    }

}