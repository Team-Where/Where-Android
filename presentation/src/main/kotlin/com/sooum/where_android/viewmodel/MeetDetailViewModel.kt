package com.sooum.where_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.sooum.domain.usecase.GetUserUseCase
import com.sooum.where_android.model.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ScreenRoute.MeetDetail>()

    private val findUserId = route.detailUserId

    fun getUserData() = getUserUseCase(findUserId)
}