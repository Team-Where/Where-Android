package com.sooum.where_android.viewmodel.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooum.domain.model.NotificationItem
import com.sooum.domain.repository.NotificationRepository
import com.sooum.domain.usecase.setting.GetNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    getNotificationUseCase: GetNotificationUseCase,
    notificationRepository: NotificationRepository
) : ViewModel() {

    val testItem = NotificationItem(
        title = "ㅎㅇ",
        description = "gdgddgdg",
        receiveTime = System.currentTimeMillis().toString()
    )

    init {
        viewModelScope.launch {
            notificationRepository.insertNotification(testItem)
        }
    }

    val notificationList = getNotificationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
}