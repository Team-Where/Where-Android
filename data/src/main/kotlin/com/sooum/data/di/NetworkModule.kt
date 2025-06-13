package com.sooum.data.di

import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
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

    private const val BASE_URL = "https://audiwhere.shop"

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient.Builder {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(
                Cache(
                    directory = File(context.cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L // 50 MiB
                )
            )
    }

    private fun makeRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                NullOnEmptyConverterFactory()
            )
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
    }

    @WhereRetrofit
    @Provides
    fun provideAuthRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder,
        tokenAuthenticator: TokenAuthenticator,
    ): Retrofit = makeRetrofit(
        okHttpClientBuilder
            .authenticator(tokenAuthenticator)
            .addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())

                // 캐시는 GET 요청에만 적용
                if (chain.request().method == "GET") {
                    originalResponse.newBuilder()
                        .removeHeader("Cache-Control") // 서버에서 온 private 제거
                        .header("Cache-Control", "public, max-age=60") // 60초 동안 캐시
                        .build()
                } else {
                    originalResponse
                }
            }
            .build()
    )

    @Provides
    @Singleton
    @NoAuthRetrofit
    fun provideRetrofit(
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit = makeRetrofit(
        okHttpClientBuilder
            .build()
    )


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