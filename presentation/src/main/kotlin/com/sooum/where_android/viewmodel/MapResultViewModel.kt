package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sooum.domain.usecase.meet.GetMeetDetailListUseCase
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class MapResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMeetDetailListUseCase: GetMeetDetailListUseCase
) : ViewModel() {
    private val shareResult = savedStateHandle.toRoute<ScreenRoute.HomeRoute.MapShareResult>()

    val meetDetailList = getMeetDetailListUseCase().transform { items ->
        emit(items.filter { !it.finished })
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}