package com.sooum.where_android.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sooum.domain.model.Friend
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.NewMeetResult
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.common.modal.LoadingScreenProvider
import com.sooum.where_android.view.common.modal.LoadingView
import com.sooum.where_android.view.main.home.HomeScreenView
import com.sooum.where_android.view.main.home.friendList.FriendMeetDetailView
import com.sooum.where_android.view.main.home.myMeet.MyMeetGuideView
import com.sooum.where_android.view.main.home.newMeet.NewMeetResultView
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
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

val LocalLoadingProvider = compositionLocalOf<LoadingScreenProvider> {
    error("CompositionLocal LocalLoadingProvider not present")
}


@Composable
fun MainScreenView(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomNavController = rememberNavController()
    val bottomNavBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val loadingScreenProvider = remember {
        LoadingScreenProvider(scope)
    }
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
            }
        },
        containerColor = Color.White,
    ) { innerPadding ->
        CompositionLocalProvider(
            values = arrayOf(
                LocalLayoutDirection provides LayoutDirection.Rtl,
                LocalLoadingProvider provides loadingScreenProvider
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
                            drawerContainerColor = Color.White
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
                        navigation<ScreenRoute.MainGraph>(startDestination = ScreenRoute.Home.Main) {
                            composable<ScreenRoute.Home.Main> {
                                HomeScreenView(
                                    navController = bottomNavController,
                                    openDrawer = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    },
                                    navigationMeetDetail = navController::navigateMeetDetail,
                                    navigationFriendDetail = navController::navigateFriendDetail,
                                    navigationGuide = {
                                        navController.navigate(ScreenRoute.Home.MeetGuide) {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                            composable<ScreenRoute.Home.MeetGuide>() {
                                MyMeetGuideView(
                                    onBack = navController::popBackStack
                                )
                            }
                            composable<ScreenRoute.Home.FriendMeetDetail>() {
                                FriendMeetDetailView(
                                    onBack = navController::popBackStack,
                                    navigationMeetDetail = navController::navigateMeetDetail
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
        if (loadingScreenProvider.showLoading) {
            LoadingView(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { },
                msg = null
            )
        }
    }
}


private fun NavHostController.navigateMeetDetail(meetDetail: MeetDetail) {
    navigateMeetDetailById(meetDetail.id)
}
private fun NavHostController.navigateMeetDetail(meetDetail: Friend.FriendMeet) {
    navigateMeetDetailById(meetDetail.meetId)
}

private fun NavHostController.navigateFriendDetail(route: ScreenRoute.Home.FriendMeetDetail) {
    navigate(route) {
        launchSingleTop = true
    }
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