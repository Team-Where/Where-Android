package com.sooum.where_android.view.hamburger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sooum.where_android.model.ScreenRoute

fun NavGraphBuilder.registerHamburgerRoute(
    mainNavController: NavHostController,
) {
    val navigateHome: () -> Unit = {
        mainNavController.navigate(ScreenRoute.HomeRoute.Main) {
            launchSingleTop = true
            popUpTo<ScreenRoute.HomeRoute.HamburgerRoute> {
                inclusive = true
            }
        }
    }
    navigation<ScreenRoute.HomeRoute.HamburgerRoute>(
        startDestination = ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit
    ) {
        composable<ScreenRoute.HomeRoute.HamburgerRoute.ProfileEdit> {
            Column(
                modifier = Modifier.safeDrawingPadding()
            ) {
                TextButton(
                    navigateHome
                ) {
                    Text("Home")
                }
                Text(text = "ProfileEdit")
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.Notification> {
            Column(
                modifier = Modifier.safeDrawingPadding()
            ) {
                TextButton(
                    navigateHome
                ) {
                    Text("Home")
                }
                Text(text = "Notification")
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.FAQ> {
            Column(
                modifier = Modifier.safeDrawingPadding()
            ) {
                TextButton(
                    navigateHome
                ) {
                    Text("Home")
                }
                Text(text = "FAQ")
            }
        }
        composable<ScreenRoute.HomeRoute.HamburgerRoute.Notice> {
            Column(
                modifier = Modifier.safeDrawingPadding()
            ) {
                TextButton(
                    navigateHome
                ) {
                    Text("Home")
                }
                Text(text = "Notice")
            }
        }
        navigation<ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute>(
            startDestination = ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry
        ) {
            composable<ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry> {
                Column(
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    TextButton(
                        navigateHome
                    ) {
                        Text("Home")
                    }
                    Text(text = "Inquiry")

                    TextButton(
                        onClick = {
                            mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Write) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text("Write")
                    }
                }
            }
            composable<ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Write> {
                Column(
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    TextButton(
                        onClick = {
                            mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry) {
                                launchSingleTop = true
                                popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.InquiryRoute.Inquiry) {

                                }
                            }
                        }
                    ) {
                        Text("Back")
                    }
                    Text(text = "Write")
                }
            }
        }

        navigation<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute>(
            startDestination = ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting
        ) {
            composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting> {
                Column(
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    TextButton(
                        navigateHome
                    ) {
                        Text("Home")
                    }
                    Text(text = "Setting")
                    TextButton(
                        onClick = {
                            mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.EditPassword) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text("Edit")
                    }
                    TextButton(
                        onClick = {
                            mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteAccount) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
            composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.EditPassword> {
                Column(
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    TextButton(
                        onClick = {
                            mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting) {
                                launchSingleTop = true
                                popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting)
                            }
                        }
                    ) {
                        Text("Back")
                    }
                    Text(text = "EditPassword")
                }
            }
            composable<ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.DeleteAccount> {
                Column(
                    modifier = Modifier.safeDrawingPadding()
                ) {
                    TextButton(
                        onClick = {
                            mainNavController.navigate(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting) {
                                launchSingleTop = true
                                popUpTo(ScreenRoute.HomeRoute.HamburgerRoute.SettingRoute.Setting)
                            }
                        }
                    ) {
                        Text("Back")
                    }
                    Text(text = "DeleteAccount")
                }
            }
        }
    }
}
