package com.sooum.where_android.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.util.Consumer
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sooum.where_android.checkAlarmScheme
import com.sooum.where_android.checkAppScheme
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.parseMapShareResult
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.view.auth.registerAuthRoute
import com.sooum.where_android.view.common.modal.LoadingScreenProvider
import com.sooum.where_android.view.common.modal.LoadingView
import com.sooum.where_android.view.hamburger.registerHamburgerRoute
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

    private var backKeyPressedTime = 0L

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis()

                    showSimpleToast("뒤로 버튼을 한번 더 누르시면 종료됩니다.")
                } else {
                    finishAndRemoveTask()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setContent {
            val mainNavController = rememberNavController()
            val scope = rememberCoroutineScope()

            val loadingScreenProvider = remember {
                LoadingScreenProvider(scope)
            }
            DisposableEffect(this@MainActivity, mainNavController) {
                val onNewIntentConsumer = Consumer<Intent> { intent ->
                    if (intent.action == "android.intent.action.MAIN" && intent.hasCategory("android.intent.category.LAUNCHER")) {
                        //By pass If Launch
                        return@Consumer
                    }
                    Log.d("JWH", intent.toString())
                    if (mainNavController.currentDestination?.hasRoute<ScreenRoute.HomeRoute.Main>() == true) {
                        intent.checkAppScheme()?.let { inviteRoute ->
                            mainNavController.navigate(inviteRoute) {
                                launchSingleTop = true
                            }
                        }
                        intent.checkAlarmScheme()?.let { id ->
                            mainNavController.navigationMeetDetailId(id)
                        }
                        intent.parseMapShareResult()?.let { shareResult ->
                            mainNavController.navigate(
                                ScreenRoute.HomeRoute.MapShareResult(
                                    shareResult
                                )
                            ) {
                                launchSingleTop = true
                            }
                        }
                    } else {
                        showSimpleToast("로그인 후 이용 가능합니다.")
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
                    registerHamburgerRoute(mainNavController)
                }
            }
            if (loadingScreenProvider.showLoading) {
                LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(0.8f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {

                        },
                    msg = null
                )
            }
        }
    }
}