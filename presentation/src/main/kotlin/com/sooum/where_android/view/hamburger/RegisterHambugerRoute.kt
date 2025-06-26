package com.sooum.where_android.view.hamburger

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.hamburger.main.EditProfileView
import com.sooum.where_android.view.hamburger.main.FaqFragment
import com.sooum.where_android.view.hamburger.main.InquiryFragment
import com.sooum.where_android.view.hamburger.main.NoticeFragment
import com.sooum.where_android.view.hamburger.main.NotificationFragment
import com.sooum.where_android.view.hamburger.main.SettingView
import com.sooum.where_android.view.hamburger.main.dialog.LogOutView
import com.sooum.where_android.view.hamburger.main.inquiry.InquiryWriteFragment
import com.sooum.where_android.view.hamburger.setting.DeleteAccountCompleteView
import com.sooum.where_android.view.hamburger.setting.DeleteAccountView
import com.sooum.where_android.view.hamburger.setting.EditPasswordFragment
import com.sooum.where_android.view.hamburger.setting.WebViewContent

/**
 * drawerRoute에서 Home으로 가는 확장
 */
internal fun NavHostController.navigateHome() {
    navigate(ScreenRoute.HomeRoute.Main) {
        launchSingleTop = true
        popUpTo<ScreenRoute.HomeRoute.HamburgerRoute> {
            inclusive = true
        }
    }
}

private val parentModifier = Modifier
    .fillMaxSize()
    .background(Color.White)
    .safeDrawingPadding()

fun NavGraphBuilder.registerHamburgerRoute(
    mainNavController: NavHostController,
) {
    navigation<ScreenRoute.HomeRoute.HamburgerRoute>(
        startDestination = ScreenRoute.HomeRoute.HamburgerRoute.Notification
    ) {
        composable<ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit> { backStackEntey ->
            Box(
                modifier = parentModifier
                    .imePadding()
            ) {
                EditProfileView(mainNavController)
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.Notification> {
            Box(
                modifier = parentModifier
            ) {
                BackHandler {
                    mainNavController.navigateHome()
                }
                AndroidFragment<NotificationFragment>(
                    modifier = Modifier.fillMaxSize()
                ) { notificationFragment ->
                    notificationFragment.setNavigation(
                        navHostController = mainNavController
                    )
                }
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.FAQ> {
            Box(
                modifier = parentModifier
            ) {
                BackHandler {
                    mainNavController.navigateHome()
                }
                AndroidFragment<FaqFragment>(
                    modifier = Modifier.fillMaxSize()
                ) { faqFragment ->
                    faqFragment.setNavigation(
                        navHostController = mainNavController
                    )
                }
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.Notice> {
            Box(
                modifier = parentModifier
            ) {
                BackHandler {
                    mainNavController.navigateHome()
                }
                AndroidFragment<NoticeFragment>(
                    modifier = Modifier.fillMaxSize()
                ) { noticeFragment ->
                    noticeFragment.setNavigation(
                        navHostController = mainNavController
                    )
                }
            }
        }
        installInquiryRoute(mainNavController)
        installSettingRoute(mainNavController)
    }
}

/**
 * 문의 관련 중첩 네비게이션
 */
private fun NavGraphBuilder.installInquiryRoute(
    mainNavController: NavHostController,
) {
    navigation<ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute>(
        startDestination = ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry
    ) {
        composable<ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry> {
            Box(
                modifier = parentModifier
            ) {
                BackHandler {
                    mainNavController.navigateHome()
                }
                AndroidFragment<InquiryFragment>(
                    modifier = Modifier.fillMaxSize()
                ) { inquiryFragment ->
                    inquiryFragment.setNavigation(
                        navHostController = mainNavController
                    )
                }
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Write> {
            Column(
                modifier = parentModifier
            ) {
                BackHandler {
                    mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry) {
                        launchSingleTop = true
                        popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry)
                    }
                }
                AndroidFragment<InquiryWriteFragment>(
                    modifier = Modifier.fillMaxSize()
                ) { fragment ->
                    fragment.setNavigation(mainNavController)
                }
            }
        }
    }
}

/**
 * 설정 관련 중첩 네비게이션
 */
private fun NavGraphBuilder.installSettingRoute(
    mainNavController: NavHostController
) {
    navigation<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute>(
        startDestination = ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting
    ) {
        composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting> {
            Box(
                modifier = parentModifier
            ) {
                SettingView(
                    controller = mainNavController
                )
            }
        }
        dialog<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Logout> {
            LogOutView(
                dismiss = {
                    mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting) {
                        launchSingleTop = true
                        popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Logout) {
                            inclusive = true
                        }
                    }
                },
                logoutAction = {
                    mainNavController.goToAuthScreen()
                }
            )
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.EditPassword> {
            Box(
                modifier = parentModifier
            ) {
                BackHandler {
                    mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting) {
                        launchSingleTop = true
                        popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting)
                    }
                }
                AndroidFragment<EditPasswordFragment>(
                    modifier = Modifier.fillMaxSize()
                ) { editPasswordFragment ->
                    editPasswordFragment.setNavigation(
                        navHostController = mainNavController
                    )
                }
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteAccount> {
            Box(
                modifier = parentModifier
            ) {
                DeleteAccountView(
                    controller = mainNavController
                )
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteComplete> {
            Box(
                modifier = parentModifier
            ) {
                DeleteAccountCompleteView(
                    controller = mainNavController
                )
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.WebView> { backStackEntry ->
            val webViewRoute: ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.WebView =
                backStackEntry.toRoute()
            Box(
                modifier = parentModifier
            ) {
                val backToSetting = {
                    mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting) {
                        launchSingleTop = true
                        popUpTo<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.WebView> {
                            inclusive = true
                        }
                    }
                }
                WebViewContent(
                    url = webViewRoute.destUrl,
                    onClick = backToSetting,
                    onBack = backToSetting
                )
            }
        }
    }
}

internal fun NavHostController.goToAuthScreen() {
    navigate(ScreenRoute.AuthRoute) {
        popUpTo(ScreenRoute.HomeRoute) {
            inclusive = true
        }
        launchSingleTop = true
    }
}