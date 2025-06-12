package com.sooum.data.di

import com.sooum.data.network.NullOnEmptyConverterFactory
import com.sooum.data.network.auth.AuthApi
import com.sooum.data.network.comment.CommentApi
import com.sooum.data.network.friend.FriendApi
import com.sooum.data.network.meet.MeetApi
import com.sooum.data.network.place.PlaceApi
import com.sooum.data.network.schedule.ScheduleApi
import com.sooum.data.network.socialLogin.SocialLoginApi
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

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthRetrofit


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

    @WhereRetrofit
    @Provides
    fun provideAuthRetrofit(
        tokenAuthenticator: TokenAuthenticator
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://audiwhere.shop")
            .client(
                OkHttpClient.Builder()
                    .authenticator(tokenAuthenticator)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(
                NullOnEmptyConverterFactory()
            )
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    @NoAuthRetrofit
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
        @NoAuthRetrofit retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKakaoApi(
        @NoAuthRetrofit retrofit: Retrofit
    ): SocialLoginApi {
        return retrofit.create(SocialLoginApi::class.java)
    }
}