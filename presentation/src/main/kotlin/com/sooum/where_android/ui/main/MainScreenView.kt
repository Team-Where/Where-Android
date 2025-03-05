package com.sooum.where_android.ui.main

import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.util.Consumer
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.sooum.domain.model.NewMeet
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.ui.main.friendList.FriendListView
import com.sooum.where_android.ui.main.meetDetail.MeetDetailView
import com.sooum.where_android.ui.main.myMeet.MyMeetView
import com.sooum.where_android.ui.main.newMeet.NewMeetResultView
import com.sooum.where_android.ui.meetInfo.CalendarModal
import com.sooum.where_android.ui.meetInfo.MapShareModal
import com.sooum.where_android.view.LocalActivity
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class ShareResult(
    val text:String
)

@Composable
fun MainScreenView(
    modifier: Modifier = Modifier
) {
    val activity = LocalActivity.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DisposableEffect(activity, navController) {
        val onNewIntentConsumer = Consumer<Intent> { intent ->
            Log.d("JWH", intent.toString())
            if (intent.action == Intent.ACTION_SEND && intent.type == "text/plain") {
                val sharedText: String = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
                Log.d("JWH", "find Text : $sharedText")
                Log.d("JWH","----")
                sharedText.split("\n").forEach {
                    Log.d("JWH","$it")
                }
                Log.d("JWH","----")
                navController.navigate(
                    ShareResult(sharedText)
                ) {
                    launchSingleTop = true
                }
            }
        }

        activity.addOnNewIntentListener(onNewIntentConsumer)

        onDispose { activity.removeOnNewIntentListener(onNewIntentConsumer) }
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
                    navBackStackEntry = navBackStackEntry,
                    navigation = { type ->
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            }
                            navController.navigate(type)
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
//                        startDestination = ScreenRoute.MainGraph,
                        startDestination = "test",
                        modifier = Modifier
                    ) {
                        composable(
                            route = "test"
                        ) {
                            Column {
                                var show by remember {
                                    mutableStateOf(false)
                                }
                                Button(
                                    onClick = {
                                        show = true
                                    }
                                ) {
                                    Text("열기")
                                }
                                if (show) {
                                    CalendarModal(
                                        onDismiss = {
                                            show = false
                                        },
                                        nextBy = {

                                        }
                                    )
                                }
                            }
                        }
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
                            dialog<NewMeetResult>(
                                dialogProperties = DialogProperties(
                                    usePlatformDefaultWidth = false,
                                    dismissOnBackPress = false
                                )
                            ) {
                                val result = it.toRoute<NewMeetResult>()
                                NewMeetResultView(
                                    result = result,
                                    close = navController::popBackStack,
                                    navigationDetail = {
                                        //TODO
                                    }
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