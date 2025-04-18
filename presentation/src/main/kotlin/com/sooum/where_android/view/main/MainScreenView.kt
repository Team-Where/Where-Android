package com.sooum.where_android.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.NewMeetResult
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.main.friendList.FriendListView
import com.sooum.where_android.view.main.meetDetail.MeetDetailView
import com.sooum.where_android.view.main.myMeet.MyMeetGuideView
import com.sooum.where_android.view.main.myMeet.MyMeetView
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import com.sooum.where_android.view.main.newMeet.NewMeetResultView
import kotlinx.coroutines.launch

/**
 * 네비게이션 숨겨야하는 경우
 */
fun NavBackStackEntry?.notShowBottom(): Boolean {
    val currentDestination = this?.destination
    return (currentDestination?.hierarchy?.any {
        it.hasRoute<ScreenRoute.Home.MeetGuide>() || it.hasRoute<ScreenRoute.MeetDetail>()
    } == true)
}

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
            val notShow = navBackStackEntry?.notShowBottom() ?: false
            AnimatedVisibility(
                visible = !notShow,
                enter = slideInVertically(
                    initialOffsetY = {
                        it / 2
                    }
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = {
                        it / 2
                    }
                ) + fadeOut()
            ) {
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
                modifier = Modifier.padding(innerPadding),
                gesturesEnabled = false
            ) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.MainGraph,
                        modifier = Modifier
                    ) {
                        composable(
                            route = "test"
                        ) {
                            Column(Modifier.fillMaxSize()){

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
                                    navigationMeetDetail = navController::navigateMeetDetail,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 10.dp,
                                        )
                                )
                            }
                            composable<ScreenRoute.Home.MeetGuide>() {
                                MyMeetGuideView(
                                    onBack = navController::popBackStack
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
                                        .padding(
                                            horizontal = 10.dp,
                                        )
                                )
                            }

                            composable<ScreenRoute.Home.FriendMeetDetail>() {
                                MeetDetailView(
                                    onBack = navController::popBackStack,
                                    navigationMeetDetail = navController::navigateMeetDetail
                                )
                            }
                            composable<ScreenRoute.MeetDetail> {
                                val route = it.toRoute<ScreenRoute.MeetDetail>()
//                                MyMeetDetailScreenView(
//                                    id = route.meetDetailId,
//                                    onBack = navController::popBackStack
//                                )
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
                                    navigationDetail = { id ->
                                        navController.popBackStack()
                                        navController.navigateMeetDetailById(id)
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


private fun NavHostController.navigateMeetDetail(meetDetail: MeetDetail) {
    navigateMeetDetailById(meetDetail.id)
}

private fun NavHostController.navigateMeetDetailById(id:Int) {
    //TODO : 환님 여기서 연결하면 됩니다.
    //
    context.startActivity(
        Intent(context, MyMeetActivity::class.java).apply {
            putExtras(Bundle().apply {
                putInt(MyMeetActivity.MEET_ID, id)
            })
        }
    )
    //Tab Layout 수정 전까지는 MyMeetActivity로 이동

//    this.navigate(ScreenRoute.MeetDetail(meetDetail.id)) {
//        launchSingleTop = true
//    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenViewPreview() {
    MainScreenView(
        Modifier
    )
}