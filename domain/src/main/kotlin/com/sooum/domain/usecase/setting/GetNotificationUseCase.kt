package com.sooum.domain.usecase.setting

import com.sooum.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke() = notificationRepository.getNotifications()
}