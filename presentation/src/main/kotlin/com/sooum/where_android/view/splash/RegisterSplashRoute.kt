package com.sooum.where_android.view.splash

import android.content.Intent
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.getInviteData

fun NavGraphBuilder.registerSplashRoute(
    mainNavController: NavController,
    intent: Intent,
) {
    navigation<ScreenRoute.SplashRoute>(
        startDestination = ScreenRoute.SplashRoute.Splash
    ) {
        composable<ScreenRoute.SplashRoute.Splash> {
            SplashView(
                nextScreen = { route ->
                    val invite = intent.getInviteData()
                    mainNavController.navigate(route) {
                        launchSingleTop = true
                        if (route !is ScreenRoute.SplashRoute.UpdateAlert) {
                            popUpTo<ScreenRoute.SplashRoute>() {
                                inclusive = true
                            }
                        }
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