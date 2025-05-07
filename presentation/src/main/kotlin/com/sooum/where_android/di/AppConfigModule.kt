package com.sooum.where_android.di

import com.sooum.core.notification.NotificationConfig
import com.sooum.where_android.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Provides
    @Singleton
    fun provideNotificationConfig(): NotificationConfig {
        return object : NotificationConfig {
            override val iconResId: Int
                get() = R.mipmap.ic_launcher
        }
    }
}