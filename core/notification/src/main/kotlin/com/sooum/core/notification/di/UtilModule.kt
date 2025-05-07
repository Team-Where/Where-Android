package com.sooum.core.notification.di

import android.content.Context
import com.sooum.core.notification.NotificationConfig
import com.sooum.core.notification.NotificationUtil
import com.sooum.core.notification.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FCMTool

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalTool

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Singleton
    @Provides
    @FCMTool
    fun provideFCMNotificationUtil(
        @ApplicationContext context: Context,
        config: NotificationConfig
    ): NotificationUtil {
        return NotificationUtil(
            context = context,
            channelId = context.getString(R.string.fcm_channel_id),
            channelName = "FCM Notification",
            description = "원격 알림을 수신 합니다.",
            config = config
        )
    }

    @Singleton
    @Provides
    @LocalTool
    fun provideLocalNotificationUtil(
        @ApplicationContext context: Context,
        config: NotificationConfig
    ): NotificationUtil {
        return NotificationUtil(
            context = context,
            channelId = "local_notification",
            channelName = "APP Notification",
            description = "앱에서 발생한 알림을 수신합니다.",
            config = config
        )
    }
}