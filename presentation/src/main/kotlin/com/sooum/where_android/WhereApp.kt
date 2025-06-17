package com.sooum.where_android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.sooum.where_android.view.share.MapShareResultActivity
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import java.lang.ref.WeakReference

@HiltAndroidApp
class WhereApp : Application(), SingletonImageLoader.Factory {

    companion object {
        var currentActivity: WeakReference<Activity>? = null
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "7e44ff67eb385fa512ec1019d33a0f1b")
        NaverIdLoginSDK.initialize(
            this,
            "SGZLJ93ZEb9rltMykhEO",        // 네이버 개발자 센터에서 발급
            "OmeoWcwqWz",    // 네이버 개발자 센터에서 발급
            "어디"          // 사용자에게 보여질 앱 이름
        )

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                updateCurrentActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                updateCurrentActivity(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                updateCurrentActivity(activity)
            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                if (currentActivity?.get() == activity) {
                    currentActivity = null
                }
            }
        })
    }


    private fun updateCurrentActivity(activity: Activity) {
        val ignoredActivities = setOf(
            MapShareResultActivity::class.java,
        )

        if (activity::class.java !in ignoredActivities) {
            currentActivity = WeakReference(activity)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {

        return ImageLoader.Builder(context)
            .components {
                add(
                    OkHttpNetworkFetcherFactory(
                        callFactory = {
                            OkHttpClient()
                        }
                    )
                )
            }
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .crossfade(true)
            .build()
    }
}