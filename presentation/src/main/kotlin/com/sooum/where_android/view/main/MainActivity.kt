package com.sooum.where_android.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.util.Consumer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sooum.where_android.checkAppScheme
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.LocalAlarmProvider
import com.sooum.where_android.view.auth.registerAuthRoute
import com.sooum.where_android.view.common.modal.LoadingScreenProvider
import com.sooum.where_android.view.common.modal.LoadingView
import com.sooum.where_android.view.getLocalAlarmProvider
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import com.sooum.where_android.view.onboarding.registerOnBoardingRoute
import com.sooum.where_android.view.splash.registerSplashRoute
import dagger.hilt.android.AndroidEntryPoint

val LocalActivity = compositionLocalOf<MainActivity> {
    error("CompositionLocal LocalActivity not present")
}

val LocalLoadingProvider = compositionLocalOf<LoadingScreenProvider> {
    error("CompositionLocal LocalLoadingProvider not present")
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        intent?.getLocalAlarmProvider()?.let {
            it.startToDetail()
        }

        setContent {
            val mainNavController = rememberNavController()
            val scope = rememberCoroutineScope()

            val loadingScreenProvider = remember {
                LoadingScreenProvider(scope)
            }
            DisposableEffect(this@MainActivity, mainNavController) {
                val onNewIntentConsumer = Consumer<Intent> {
                    it.checkAppScheme()?.let {
                        mainNavController.navigate(it)
                    }
                }

                this@MainActivity.addOnNewIntentListener(onNewIntentConsumer)

                onDispose { this@MainActivity.removeOnNewIntentListener(onNewIntentConsumer) }
            }
            CompositionLocalProvider(
                LocalActivity provides this@MainActivity,
                LocalLoadingProvider provides loadingScreenProvider
            ) {
                NavHost(
                    navController = mainNavController,
                    startDestination = ScreenRoute.SplashRoute
                ) {
                    registerSplashRoute(mainNavController, intent)
                    registerOnBoardingRoute(mainNavController)
                    registerAuthRoute(mainNavController)
                    registerHomeRoute(mainNavController)
                }
            }
            if (loadingScreenProvider.showLoading) {
                LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { },
                    msg = null
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getLocalAlarmProvider()?.let {
            it.startToDetail()
        }
    }

    private fun LocalAlarmProvider.startToDetail() {
        val id = meetId
        startActivity(
            Intent(this@MainActivity, MyMeetActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putInt(MyMeetActivity.MEET_ID, id)
                })
            }
        )
    }
}