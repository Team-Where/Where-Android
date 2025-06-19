package com.sooum.where_android.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.usecase.user.UpdateFcmToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [com.sooum.where_android.view.main.MainScreenView] 에서 사용되는 viewmodel
 */
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val updateFcmToken: UpdateFcmToken
) : ViewModel() {

    fun updateFcm(token: String) {
        viewModelScope.launch {
            updateFcmToken(token)
        }
    }
}