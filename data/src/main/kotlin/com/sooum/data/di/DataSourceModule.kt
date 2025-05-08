package com.sooum.data.di

import com.sooum.data.datasource.MeetRemoteDataSourceImpl
import com.sooum.domain.datasource.MeetRemoteDataSource
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

}