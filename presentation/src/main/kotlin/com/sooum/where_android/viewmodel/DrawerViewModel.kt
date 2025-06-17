package com.sooum.where_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import com.sooum.domain.usecase.user.GetLoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    getLoginUserUseCase: GetLoginUserUseCase,
    getMeetListUseCase: GetMeetDetailListUseCase
) : ViewModel() {

    val myPage = getLoginUserUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            null
        )

    val meetCount = getMeetListUseCase().transform {
        emit(it.size)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        0
    )

}