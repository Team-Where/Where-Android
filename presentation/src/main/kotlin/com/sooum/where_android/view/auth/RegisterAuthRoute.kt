package com.sooum.where_android.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.view.auth.signup.AgreementFragment
import com.sooum.where_android.view.auth.signup.EmailVerificationFragment
import com.sooum.where_android.view.auth.signup.PasswordFragment
import com.sooum.where_android.view.auth.signup.ProfileSettingFragment
import com.sooum.where_android.view.auth.signup.SignUpCompleteFragment
import com.sooum.where_android.view.auth.signup.SocialLoginProfileSettingFragment
import com.sooum.where_android.view.hamburger.setting.WebViewContent

fun NavGraphBuilder.registerAuthRoute(
    mainNavController: NavHostController,
) {
    navigation<ScreenRoute.AuthRoute>(
        startDestination = ScreenRoute.AuthRoute.SocialLogin
    ) {
        composable<ScreenRoute.AuthRoute.SocialLogin> {
            AndroidFragment<SocialLoginFragment>(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .systemBarsPadding()
            ) { socialLoginFragment ->
                socialLoginFragment.setNavigation(mainNavController)
            }
        }
        composable<ScreenRoute.AuthRoute.SignIn> {
            AndroidFragment<SignInFragment>(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .systemBarsPadding()
            ) { signInFragment ->
                signInFragment.setNavigation(mainNavController)
            }
        }
        composable<ScreenRoute.AuthRoute.SocialProfile> {
            AndroidFragment<SocialLoginProfileSettingFragment>(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .systemBarsPadding()
            ) { socialLoginProfileSettingFragment ->
                socialLoginProfileSettingFragment.setNavigation(mainNavController)
            }
        }
        navigation<ScreenRoute.AuthRoute.SingUpRoute>(
            startDestination = ScreenRoute.AuthRoute.SingUpRoute.Agreement
        ) {
            composable<ScreenRoute.AuthRoute.SingUpRoute.Agreement> {
                AndroidFragment<AgreementFragment>(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .systemBarsPadding()
                ) { agreementFragment ->
                    agreementFragment.setNavigation(mainNavController)
                }
            }
            composable<ScreenRoute.AuthRoute.SingUpRoute.EmailVerification> {
                AndroidFragment<EmailVerificationFragment>(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .systemBarsPadding()
                ) { emailVerificationFragment ->
                    emailVerificationFragment.setNavigation(mainNavController)
                }
            }
            composable<ScreenRoute.AuthRoute.SingUpRoute.Password> {
                AndroidFragment<PasswordFragment>(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .systemBarsPadding()
                ) { passwordFragment ->
                    passwordFragment.setNavigation(mainNavController)
                }
            }
            composable<ScreenRoute.AuthRoute.SingUpRoute.Profile> {
                AndroidFragment<ProfileSettingFragment>(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .systemBarsPadding()
                ) { profileSettingFragment ->
                    profileSettingFragment.setNavigation(mainNavController)
                }
            }
            composable<ScreenRoute.AuthRoute.SingUpRoute.Complete> {
                AndroidFragment<SignUpCompleteFragment>(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .systemBarsPadding()
                ) { signUpCompleteFragment ->
                    signUpCompleteFragment.setNavigation(mainNavController)
                }
            }
            composable<ScreenRoute.AuthRoute.SingUpRoute.WebView> { backStackEntry ->
                val webViewRoute: ScreenRoute.AuthRoute.SingUpRoute.WebView =
                    backStackEntry.toRoute()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .safeDrawingPadding()
                ) {
                    WebViewContent(
                        url = webViewRoute.destUrl,
                        onClick = mainNavController::backToAgree,
                        onBack = mainNavController::backToAgree,
                    )
                }
            }
        }
    }
}


internal fun NavHostController.navigateSignIn(
    applyOption: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(ScreenRoute.AuthRoute.SignIn) {
        launchSingleTop = true
        this.applyOption()
    }
}


internal fun NavHostController.navigateSocialProfile(
    id: Int
) {
    navigate(ScreenRoute.AuthRoute.SocialProfile(id)) {
        launchSingleTop = true
    }
}

internal fun NavHostController.navigateSignUp() {
    navigate(ScreenRoute.AuthRoute.SingUpRoute) {
        launchSingleTop = true
    }
}

internal fun NavHostController.backToAgree() {
    navigate(ScreenRoute.AuthRoute.SingUpRoute.Agreement) {
        launchSingleTop = true
        popUpTo<ScreenRoute.AuthRoute.SingUpRoute.WebView> {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateEmailVerification() {
    navigate(ScreenRoute.AuthRoute.SingUpRoute.EmailVerification) {
        launchSingleTop = true
    }
}

internal fun NavHostController.navigatePassword() {
    navigate(ScreenRoute.AuthRoute.SingUpRoute.Password) {
        launchSingleTop = true
    }
}

internal fun NavHostController.navigateProfile() {
    navigate(ScreenRoute.AuthRoute.SingUpRoute.Profile) {
        launchSingleTop = true
    }
}

internal fun NavHostController.navigateComplete() {
    navigate(ScreenRoute.AuthRoute.SingUpRoute.Complete) {
        launchSingleTop = true
    }
}

internal fun NavHostController.navigateHome() {
    navigate(ScreenRoute.HomeRoute) {
        launchSingleTop = true
        popUpTo<ScreenRoute.AuthRoute> {
            inclusive = true
        }
    }
}