package com.sooum.data.repository

import com.sooum.data.room.NotificationDao
import com.sooum.domain.model.NotificationItem
import com.sooum.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dao: NotificationDao,
) : NotificationRepository {

    override suspend fun insertNotification(notificationItem: NotificationItem) {
       dao.insert(notificationItem)
    }

    override fun getNotifications(): Flow<List<NotificationItem>> = dao.getAllNotifications()
}