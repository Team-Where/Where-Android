package com.sooum.where_android.viewmodel.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.usecase.setting.GetNotificationUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    getNotificationUseCase: GetNotificationUseCase
) : ViewModel() {

    val notificationList = getNotificationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
}