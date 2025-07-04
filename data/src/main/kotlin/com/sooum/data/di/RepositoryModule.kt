package com.sooum.data.di

import com.sooum.data.network.setting.SettingApi
import com.sooum.data.repository.AuthRepositoryImpl
import com.sooum.data.repository.FriendRepositoryImpl
import com.sooum.data.repository.MeetDetailCommentRepositoryImpl
import com.sooum.data.repository.MeetDetailPlaceRepositoryImpl
import com.sooum.data.repository.MeetDetailRepositoryImpl
import com.sooum.data.repository.NotificationRepositoryImpl
import com.sooum.data.repository.SettingRepositoryImpl
import com.sooum.data.repository.SocialLoginRepositoryImpl
import com.sooum.data.repository.UserRepositoryImpl
import com.sooum.domain.repository.AuthRepository
import com.sooum.domain.repository.FriendRepository
import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.repository.MeetDetailRepository
import com.sooum.domain.repository.NotificationRepository
import com.sooum.domain.repository.SettingRepository
import com.sooum.domain.repository.SocialLoginRepository
import com.sooum.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindFriendRepository(
        friendRepositoryImpl: FriendRepositoryImpl
    ): FriendRepository

    @Binds
    @Singleton
    abstract fun bindMeetDetailRepository(
        meetDetailRepositoryImpl: MeetDetailRepositoryImpl
    ): MeetDetailRepository

    @Binds
    @Singleton
    abstract fun bindMeetDetailPlaceRepository(
        meetDetailPlaceRepositoryImpl: MeetDetailPlaceRepositoryImpl
    ): MeetDetailPlaceRepository

    @Binds
    @Singleton
    abstract fun bindMeetDetailCommentRepository(
        meetDetailCommentRepositoryImpl: MeetDetailCommentRepositoryImpl
    ): MeetDetailCommentRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindSocialLoginRepository(
        impl: SocialLoginRepositoryImpl
    ): SocialLoginRepository

    @Binds
    @Singleton
    abstract fun settingRepository(
        impl: SettingRepositoryImpl
    ): SettingRepository

    @Binds
    @Singleton
    abstract fun notificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

}