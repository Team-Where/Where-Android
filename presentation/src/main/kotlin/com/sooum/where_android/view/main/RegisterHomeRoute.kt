package com.sooum.where_android.view.main

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.sooum.domain.model.NewMeetResult
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.invite.SchemeView
import com.sooum.where_android.view.main.home.myMeet.MyMeetGuideView
import com.sooum.where_android.view.main.home.newMeet.NewMeetResultView
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import com.sooum.where_android.view.share.MapPlaceResultView

fun NavGraphBuilder.registerHomeRoute(
    mainNavController: NavHostController
) {
    navigation<ScreenRoute.HomeRoute>(
        startDestination = ScreenRoute.HomeRoute.Main
    ) {
        composable<ScreenRoute.HomeRoute.Main> {
            MainScreenView(
                navController = mainNavController,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable<ScreenRoute.HomeRoute.MeetGuide>() {
            MyMeetGuideView(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .safeDrawingPadding(),
                onBack = mainNavController::backToHome
            )
        }
        composable<NewMeetResult>() {
            val result = it.toRoute<NewMeetResult>()
            NewMeetResultView(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .safeDrawingPadding(),
                result = result,
                close = mainNavController::backToHome,
                navigationDetail = { id ->
                    if (mainNavController.backToHome()) {
                        mainNavController.navigationMeetDetailId(id)
                    }
                }
            )
        }
        composable<ScreenRoute.HomeRoute.InviteByCode> {
            SchemeView(
                navigateHome = {
                    mainNavController.navigate(ScreenRoute.HomeRoute.Main) {
                        launchSingleTop = true
                        popUpTo<ScreenRoute.HomeRoute.InviteByCode> {
                            inclusive = true
                        }
                    }
                },
                navigateMeet = { id ->
                    mainNavController.navigate(ScreenRoute.HomeRoute.Main) {
                        launchSingleTop = true
                        popUpTo<ScreenRoute.HomeRoute.InviteByCode> {
                            inclusive = true
                        }
                    }
                    mainNavController.navigationMeetDetailId(id)

                }
            )
        }

        composable<ScreenRoute.HomeRoute.MapShareResult> {
            MapPlaceResultView(
                meetDetailList = emptyList(),
                navigateHome = {
                    mainNavController.navigate(ScreenRoute.HomeRoute.Main) {
                        launchSingleTop = true
                        popUpTo<ScreenRoute.HomeRoute.MapShareResult> {
                            inclusive = true
                        }
                    }
                },
                navigateMeet = { id ->
                    mainNavController.navigate(ScreenRoute.HomeRoute.Main) {
                        launchSingleTop = true
                        popUpTo<ScreenRoute.HomeRoute.MapShareResult> {
                            inclusive = true
                        }
                    }
                    mainNavController.navigationMeetDetailId(id)
                }
            )
        }
    }
}

internal fun NavHostController.backToHome(): Boolean {
    return popBackStack<ScreenRoute.HomeRoute.Main>(inclusive = false)
}

internal fun NavHostController.navigationMeetDetailId(id: Int) {
    context.startActivity(
        Intent(context, MyMeetActivity::class.java).apply {
            putExtras(Bundle().apply {
                putInt(MyMeetActivity.MEET_ID, id)
            })
        }
    )
}
