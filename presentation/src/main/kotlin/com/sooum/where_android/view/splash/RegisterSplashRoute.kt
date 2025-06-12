package com.sooum.where_android.view.splash

import android.content.Intent
import android.util.Log
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.sooum.where_android.checkAlarmScheme
import com.sooum.where_android.checkAppScheme
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.main.navigationMeetDetailId

fun NavGraphBuilder.registerSplashRoute(
    mainNavController: NavHostController,
    intent: Intent,
) {
    navigation<ScreenRoute.SplashRoute>(
        startDestination = ScreenRoute.SplashRoute.Splash
    ) {
        composable<ScreenRoute.SplashRoute.Splash> {
            SplashView(
                showAlert = { alertRoute ->
                    mainNavController.navigate(alertRoute) {
                        launchSingleTop = true
                    }
                },
                nextScreen = { route ->
                    if (route is ScreenRoute.OnBoarding ||
                        route is ScreenRoute.AuthRoute
                    ) {
                        mainNavController.navigateNext(route)
                    } else {
                        Log.d("JWH", intent.toString())

                        //스키마 혹은 앱링크로 실행된 경우 체크
                        intent.checkAppScheme()?.let {
                            mainNavController.navigateNext(it)
                            return@SplashView
                        }

                        Log.d("JWH", intent.action.toString())

                        //알람데이터로 부터 id를 가져온 경우 추가 실행
                        intent.checkAlarmScheme()?.let { id ->
                            mainNavController.navigationMeetDetailId(id)
                        }
                        //어떤 조건도 걸리지 않은 경우 그냥 진행 한다.
                        mainNavController.navigateNext(route)
                    }
                },
            )
        }
        dialog<ScreenRoute.SplashRoute.UpdateAlert>(
            dialogProperties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            )
        ) {
            ForceUpdateView()
        }

        dialog<ScreenRoute.SplashRoute.ErrorAlert>() {

        }
    }
}

internal fun NavHostController.navigateNext(
    route: ScreenRoute
) {
    navigate(route) {
        launchSingleTop = true
        popUpTo<ScreenRoute.SplashRoute>() {
            inclusive = true
        }
    }
}