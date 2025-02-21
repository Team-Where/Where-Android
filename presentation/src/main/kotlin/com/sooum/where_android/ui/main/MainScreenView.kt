package com.sooum.where_android.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sooum.where_android.model.BottomNavigationType
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.ui.friendList.FriendListView
import com.sooum.where_android.viewmodel.UserViewModel


@Composable
fun MainScreenView(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation(
                navBackStackEntry = navBackStackEntry,
                navigation = { type ->
                    navController.navigate(type)
                }
            )
        },
        containerColor = Color.White,
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Main
        ) {
            navigation<ScreenRoute.Main>(startDestination = BottomNavigationType.MeetList) {
                composable<BottomNavigationType.MeetList>() {
                    Column {
                        Text(navBackStackEntry?.destination?.route ?: "")
                        Text(BottomNavigationType.MeetList.toString())
                        Text(BottomNavigationType.FriendsList.toString())
                    }

                }
                composable<BottomNavigationType.FriendsList>() {
                    val userViewModel = hiltViewModel<UserViewModel>()
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(
                                horizontal = 10.dp,
                            )
                    ) {
                        FriendListView(
                            userViewModel = userViewModel,
                            navigationMeetDetail = { meetDetail ->
                                navController.navigate(meetDetail)
                            }
                        )
                    }
                }
            }

            composable<ScreenRoute.MeetDetail>() {
                val meetDetail : ScreenRoute.MeetDetail = it.toRoute()
                Text(meetDetail.toString())
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenViewPreview() {
    MainScreenView(
        Modifier
    )
}