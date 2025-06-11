package com.sooum.where_android.view.main.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.main.home.friendList.FriendListView
import com.sooum.where_android.view.main.home.friendList.FriendMeetDetailView
import com.sooum.where_android.view.main.home.myMeet.MyMeetView

@Composable
fun HomeScreenView(
    navController: NavHostController,
    openDrawer: () -> Unit,
    navigationMeetDetailId: (id: Int) -> Unit,
    navigationGuide: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeRoute.Main.BottomNavigation.MeetList,
        modifier = Modifier.padding(
            horizontal = 10.dp
        )
    ) {
        composable<ScreenRoute.HomeRoute.Main.BottomNavigation.MeetList>() {
            MyMeetView(
                openDrawer = openDrawer,
                navigationGuide = navigationGuide,
                navigationMeetDetail = {
                    navigationMeetDetailId(it.id)
                },
                modifier = Modifier
            )
        }
        composable<ScreenRoute.HomeRoute.Main.BottomNavigation.FriendsList>() {
            FriendListView(
                navigationFriendDetail = { friendDetail ->
                    navController.navigate(friendDetail) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
            )
        }
        composable<ScreenRoute.HomeRoute.Main.BottomNavigation.FriendMeetDetail>() {
            FriendMeetDetailView(
                onBack = navController::popBackStack,
                navigationMeetDetail = {
                    navigationMeetDetailId(it.meetId)
                }
            )
        }
    }
}