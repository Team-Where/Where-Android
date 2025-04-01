package com.sooum.data.di

import com.sooum.data.repository.MeetDetailRepositoryImpl
import com.sooum.data.repository.UserRepositoryImpl
import com.sooum.domain.repository.MeetDetailRepository
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
    abstract fun bindMeetDetailRepository(
        meetDetailRepositoryImpl: MeetDetailRepositoryImpl
    ): MeetDetailRepository
}