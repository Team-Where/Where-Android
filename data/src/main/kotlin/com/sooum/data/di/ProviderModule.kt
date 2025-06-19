package com.sooum.data.di

import com.sooum.data.provider.FcmTokenProviderImpl
import com.sooum.data.provider.TokenProviderImpl
import com.sooum.domain.provider.FcmTokenProvider
import com.sooum.domain.provider.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    @Singleton
    abstract fun bindTokenProvider(
        impl: TokenProviderImpl
    ): TokenProvider

    @Binds
    @Singleton
    abstract fun bindFcmTokenProvider(
        impl: FcmTokenProviderImpl
    ): FcmTokenProvider
}