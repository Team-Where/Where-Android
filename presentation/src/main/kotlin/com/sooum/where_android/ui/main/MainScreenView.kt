package com.sooum.where_android.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.ui.main.friendList.FriendListView
import com.sooum.where_android.ui.main.meetDetail.MeetDetailView
import com.sooum.where_android.ui.main.myMeet.MyMeetView
import kotlinx.coroutines.launch

@Composable
fun MainScreenView(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(
                contentColor = Color.White,
                containerColor = Color.White,
                contentPadding = PaddingValues(0.dp)
            ) {
                BottomNavigation(
                    navBackStackEntry = navBackStackEntry,
                    navigation = { type ->
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            }
                            navController.navigate(type)
                        }
                    }
                )
            }
        },
        containerColor = Color.White,
    ) { innerPadding ->
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    CompositionLocalProvider(
                        LocalLayoutDirection provides LayoutDirection.Ltr
                    ) {
                        BackHandler(
                            drawerState.isOpen
                        ) {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                        ModalDrawerSheet(
                            drawerState = drawerState,
                            drawerShape = RectangleShape
                        ) {
                            DrawerContent(
                                closeDrawer = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                },
                modifier = Modifier.padding(innerPadding)
            ) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.MainGraph,
                        modifier = Modifier
                    ) {
                        navigation<ScreenRoute.MainGraph>(startDestination = ScreenRoute.BottomNavigation.MeetList) {
                            composable<ScreenRoute.BottomNavigation.MeetList>() {
                                MyMeetView(
                                    openDrawer = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    },
                                    navigationGuide = {
                                        navController.navigate(ScreenRoute.Home.MeetGuide) {
                                            launchSingleTop = true
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 10.dp,
                                        )
                                )
                            }
                            composable<ScreenRoute.Home.MeetGuide>() {

                            }
                            composable<ScreenRoute.BottomNavigation.FriendsList>() {
                                FriendListView(
                                    navigationMeetDetail = { meetDetail ->
                                        navController.navigate(meetDetail) {
                                            launchSingleTop = true
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 10.dp,
                                        )
                                )
                            }

                            composable<ScreenRoute.Home.FriendMeetDetail>() {
                                MeetDetailView(
                                    onBack = navController::popBackStack
                                )
                            }
                        }
                    }
                }
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