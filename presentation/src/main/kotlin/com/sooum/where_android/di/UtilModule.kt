package com.sooum.where_android.di

import android.content.Context
import androidx.core.content.ContextCompat
import com.sooum.where_android.R
import com.sooum.where_android.util.NotificationUtil
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
        @ApplicationContext context: Context
    ): NotificationUtil {
        return NotificationUtil(
            context = context,
            channelId = ContextCompat.getString(context, R.string.fcm_channel_id),
            channelName = "FCM Notification",
            description = "원격 알림을 수신 합니다."
        )
    }

    @Singleton
    @Provides
    @LocalTool
    fun provideLocalNotificationUtil(
        @ApplicationContext context: Context
    ): NotificationUtil {
        return NotificationUtil(
            context = context,
            channelId = "local_notification",
            channelName = "APP Notification",
            description = "앱에서 발생한 알림을 수신합니다."
        )
    }
}