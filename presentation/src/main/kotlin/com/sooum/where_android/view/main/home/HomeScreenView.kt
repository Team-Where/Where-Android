package com.sooum.where_android.view.main.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.main.home.friendList.FriendListView
import com.sooum.where_android.view.main.home.myMeet.MyMeetView

@Composable
fun HomeScreenView(
    navController: NavHostController,
    openDrawer: () -> Unit,
    navigationMeetDetail: (MeetDetail) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.BottomNavigation.MeetList,
        modifier = Modifier.padding(
            horizontal = 10.dp
        )
    ) {
        composable<ScreenRoute.BottomNavigation.MeetList>() {
            MyMeetView(
                openDrawer = openDrawer,
                navigationGuide = {
                    navController.navigate(ScreenRoute.Home.MeetGuide) {
                        launchSingleTop = true
                    }
                },
                navigationMeetDetail = navigationMeetDetail,
                modifier = Modifier
            )
        }
        composable<ScreenRoute.BottomNavigation.FriendsList>() {
            FriendListView(
                navigationMeetDetail = { meetDetail ->
                    navController.navigate(meetDetail) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
            )
        }
    }
}