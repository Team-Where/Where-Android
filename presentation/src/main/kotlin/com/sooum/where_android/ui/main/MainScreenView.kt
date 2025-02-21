package com.sooum.where_android.ui.main

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.sooum.where_android.model.BottomNavigationType
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.ui.main.friendList.FriendListView
import com.sooum.where_android.ui.main.meetDetail.MeetDetailView
import com.sooum.where_android.ui.main.meetList.MeetListView
import kotlinx.coroutines.launch

@Composable
fun MainScreenView(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    ModalDrawerSheet {
                        DrawerContent(
                            closeDrawer = {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                }
            }
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Ltr
            ) {
                Scaffold(
                    modifier = modifier,
                    bottomBar = {
                        BottomAppBar(
                            contentColor = Color.White,
                            containerColor = Color.White
                        ) {
                            BottomNavigation(
                                navBackStackEntry = navBackStackEntry,
                                navigation = { type ->
                                    navController.navigate(type)
                                }
                            )
                        }
                    },
                    containerColor = Color.White,
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.Main,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<ScreenRoute.Main>(startDestination = BottomNavigationType.MeetList) {
                            composable<BottomNavigationType.MeetList>() {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 10.dp,
                                        )
                                ) {
                                    MeetListView(
                                        openDrawer = {
                                            scope.launch {
                                                drawerState.apply {
                                                    if (isClosed) open() else close()
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                            composable<BottomNavigationType.FriendsList>() {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 10.dp,
                                        )
                                ) {
                                    FriendListView(
                                        navigationMeetDetail = { meetDetail ->
                                            navController.navigate(meetDetail)
                                        }
                                    )
                                }
                            }
                        }

                        composable<ScreenRoute.MeetDetail>() {
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

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenViewPreview() {
    MainScreenView(
        Modifier
    )
}