package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sooum.domain.usecase.GetUserUseCase
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MeetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserUseCase: GetUserUseCase,
    private val getMeetDetailListUseCase: GetMeetDetailListUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ScreenRoute.MeetDetail>()

    private val findUserId = route.detailUserId

    fun getUserData() = getUserUseCase(findUserId)

    val meetDetailList = getMeetDetailListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )
}