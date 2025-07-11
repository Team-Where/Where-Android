package com.sooum.where_android.view.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sooum.where_android.model.ScreenRoute

fun NavGraphBuilder.registerOnBoardingRoute(
    mainNavController: NavHostController
) {
    composable<ScreenRoute.OnBoarding>() {
        AndroidFragment<OnBoardingFragment>(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .safeDrawingPadding()
        ) { fragment ->
            fragment.setNavigation(
                navHostController = mainNavController
            )
        }
    }
}

internal fun NavHostController.navigateAuth() {
    navigate(ScreenRoute.AuthRoute) {
        launchSingleTop = true
        popUpTo<ScreenRoute.OnBoarding>() {
            inclusive = true
        }
    }
}