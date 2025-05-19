package com.sooum.data.di

import com.sooum.data.network.NullOnEmptyConverterFactory
import com.sooum.data.network.auth.AuthApi
import com.sooum.data.network.comment.CommentApi
import com.sooum.data.network.friend.FriendApi
import com.sooum.data.network.kakao.KakaoApi
import com.sooum.data.network.meet.MeetApi
import com.sooum.data.network.place.PlaceApi
import com.sooum.data.network.schedule.ScheduleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WhereRetrofit


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @WhereRetrofit
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://audiwhere.shop")
            .addConverterFactory(
                NullOnEmptyConverterFactory()
            )
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()

    @Provides
    @Singleton
    fun provideFriendApi(
        @WhereRetrofit retrofit: Retrofit
    ): FriendApi {
        return retrofit.create(FriendApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMeetApi(
        @WhereRetrofit retrofit: Retrofit
    ): MeetApi {
        return retrofit.create(MeetApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlaceApi(
        @WhereRetrofit retrofit: Retrofit
    ): PlaceApi {
        return retrofit.create(PlaceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentApi(
        @WhereRetrofit retrofit: Retrofit
    ): CommentApi {
        return retrofit.create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleApi(
        @WhereRetrofit retrofit: Retrofit
    ): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        @WhereRetrofit retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKakaoApi(
        @WhereRetrofit retrofit: Retrofit
    ): KakaoApi {
        return retrofit.create(KakaoApi::class.java)
    }
}