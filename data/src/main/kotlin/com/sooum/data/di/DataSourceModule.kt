package com.sooum.data.di

import com.sooum.data.datasource.FriendRemoteDataSourceImpl
import com.sooum.data.datasource.MeetRemoteDataSourceImpl
import com.sooum.data.datasource.NoticeRemoteDataSourceImpl
import com.sooum.data.datasource.UserRemoteDataSourceImpl
import com.sooum.domain.datasource.FriendRemoteDataSource
import com.sooum.domain.datasource.MeetRemoteDataSource
import com.sooum.domain.datasource.NoticeRemoteDataSource
import com.sooum.domain.datasource.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMeetRemoteSource(
        meetRemoteDataSource: MeetRemoteDataSourceImpl
    ): MeetRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindFriendRemoteSource(
        friendRemoteDataSource: FriendRemoteDataSourceImpl
    ): FriendRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteSource(
        userRemoteDataSource: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNoticeRemoteSource(
        noticeRemoteDataSource: NoticeRemoteDataSourceImpl
    ): NoticeRemoteDataSource

}