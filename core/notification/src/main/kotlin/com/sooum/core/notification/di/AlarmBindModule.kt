package com.sooum.core.notification.di

import com.sooum.core.alarm.AlarmMaker
import com.sooum.core.notification.alarm.LocalAlarmManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AlarmBindModule {

    @Singleton
    @Binds
    abstract fun bindsAlarmMaker(
        localAlarmManager: LocalAlarmManager
    ): AlarmMaker
}