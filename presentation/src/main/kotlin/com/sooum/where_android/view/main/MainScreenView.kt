package com.sooum.where_android.view.main

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.main.home.HomeScreenView
import com.sooum.where_android.viewmodel.main.MainScreenViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Composable
fun MainScreenView(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val bottomNavController = rememberNavController()
    val bottomNavBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                mainScreenViewModel.updateFcm(token)
            }
        }
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { result ->

    }

    LaunchedEffect(true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(
                contentColor = Color.White,
                containerColor = Color.White,
                contentPadding = PaddingValues(0.dp)
            ) {
                BottomNavigation(
                    navBackStackEntry = bottomNavBackStackEntry,
                    navigation = { type ->
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            }
                            bottomNavController.navigate(type) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    navigationResult = {
                        navController.navigate(it) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        },
        containerColor = Color.White,
    ) { innerPadding ->
        CompositionLocalProvider(
            values = arrayOf(
                LocalLayoutDirection provides LayoutDirection.Rtl,
            )
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
                            drawerShape = RectangleShape,
                            drawerContainerColor = Color.White,
                            windowInsets = WindowInsets(0.dp)
                        ) {
                            DrawerContent(
                                modifier = Modifier.fillMaxSize(),
                                closeDrawer = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                navigate = {
                                    scope.launch {
                                        async {
                                            drawerState.close()
                                        }
                                        async {
                                            navController.navigate(it) {
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                },
                modifier = Modifier.padding(innerPadding),
                gesturesEnabled = true
            ) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    HomeScreenView(
                        navController = bottomNavController,
                        openDrawer = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        },
                        navigationMeetDetailId = navController::navigationMeetDetailId,
                        navigationGuide = {
                            navController.navigate(ScreenRoute.HomeRoute.MeetGuide) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenViewPreview() {
    MainScreenView(
        navController = rememberNavController(),
        Modifier
    )
}