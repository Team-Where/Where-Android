package com.sooum.where_android.view.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.where_android.R
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.view.main.LocalActivity
import com.sooum.where_android.viewmodel.SplashViewModel

@Composable
internal fun SplashView(
    splashViewModel: SplashViewModel = hiltViewModel(),
    showAlert: (ScreenRoute) -> Unit,
    nextScreen: (ScreenRoute) -> Unit,
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    LaunchedEffect(true) {
        splashViewModel.checkSplash(
            needUpdate = {
                showAlert(ScreenRoute.SplashRoute.UpdateAlert)
            },
            complete = { dest ->
                nextScreen(dest)
            },
            onVersionCheckFailed = {
                context.showSimpleToast("업데이트 정보를 가져올 수 없습니다.\n앱을 종료합니다.")
                activity.finish()
//                showAlert(ScreenRoute.SplashRoute.ErrorAlert)
            }

        )
    }
    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary600)
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(R.drawable.image_where_non_color),
            contentDescription = "where logo"
        )
    }
}

@Preview
@Composable
private fun SplashContentPreview() {
    SplashContent()
}