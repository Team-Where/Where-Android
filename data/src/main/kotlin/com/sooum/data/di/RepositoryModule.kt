package com.sooum.data.di

import com.sooum.data.repository.AuthRepositoryImpl
import com.sooum.data.repository.FriendRepositoryImpl
import com.sooum.data.repository.KakaoRepositoryImpl
import com.sooum.data.repository.MeetDetailCommentRepositoryImpl
import com.sooum.data.repository.MeetDetailPlaceRepositoryImpl
import com.sooum.data.repository.MeetDetailRepositoryImpl
import com.sooum.domain.repository.AuthRepository
import com.sooum.domain.repository.FriendRepository
import com.sooum.domain.repository.KakaoRepository
import com.sooum.domain.repository.MeetDetailCommentRepository
import com.sooum.domain.repository.MeetDetailPlaceRepository
import com.sooum.domain.repository.MeetDetailRepository
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
    abstract fun bindFriendRepository(
        userRepositoryImpl: FriendRepositoryImpl
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
    abstract fun bindKakaoRepository(
        impl: KakaoRepositoryImpl
    ): KakaoRepository
}