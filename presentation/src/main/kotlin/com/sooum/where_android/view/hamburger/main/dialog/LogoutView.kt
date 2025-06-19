package com.sooum.where_android.view.hamburger.main.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.where_android.showSimpleToast
import com.sooum.where_android.theme.GrayScale800
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.main.LocalLoadingProvider
import com.sooum.where_android.view.widget.GrayButton
import com.sooum.where_android.view.widget.PrimaryButton
import com.sooum.where_android.viewmodel.hambuger.LogoutViewModel

@Composable
fun LogOutView(
    logoutViewModel: LogoutViewModel = hiltViewModel(),
    dismiss: () -> Unit = {},
    logoutAction: () -> Unit = {}
) {
    val loadingScreenProvider = LocalLoadingProvider.current
    val context = LocalContext.current

    LogoutContent(
        cancelAction = dismiss,
        logoutAction = {
            loadingScreenProvider.startLoading()
            logoutViewModel.logout(
                onSuccess = {
                    loadingScreenProvider.stopLoading {
                        logoutAction()
                    }
                },
                onFail = { msg ->
                    loadingScreenProvider.stopLoading {
                        context.showSimpleToast(msg)
                    }
                }
            )
        }
    )
}

@Composable
private fun LogoutContent(
    cancelAction: () -> Unit = {},
    logoutAction: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "정말 로그아웃 하시겠습니까?",
            fontFamily = pretendard,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = GrayScale800
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GrayButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .weight(1f),
                onClick = cancelAction,
                title = "취소",
                radius = 10.dp
            )
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .weight(1f),
                onClick = logoutAction,
                title = "로그아웃",
                radius = 10.dp
            )
        }
    }
}

@Composable
@Preview
private fun LogoutPreview() {
    LogoutContent()
}