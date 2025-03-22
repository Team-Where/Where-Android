package com.sooum.data.di

import android.content.Context
import com.sooum.data.util.UriConverterImpl
import com.sooum.domain.util.UriConverter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    @Singleton
    abstract fun bindUriConverter(
        uriConverterImpl: UriConverterImpl
    ): UriConverter
}