package com.sooum.domain.repository

import com.sooum.domain.model.NotificationItem
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun insertNotification(notificationItem: NotificationItem)

    fun getNotifications(): Flow<List<NotificationItem>>
}