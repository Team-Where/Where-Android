package com.sooum.where_android.di

import android.content.Context
import android.content.Intent
import com.sooum.core.notification.AlarmOption
import com.sooum.core.notification.NotificationConfig
import com.sooum.where_android.R
import com.sooum.where_android.view.main.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAlarmOption(
        @ApplicationContext context: Context
    ): AlarmOption {
        return object : AlarmOption {
            override fun makeIntent(): Intent {
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                return intent
            }
        }
    }
}