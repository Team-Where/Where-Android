package com.sooum.where_android.view.share

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.theme.pretendard

@Composable
fun MapPlaceResultView(
    meetDetailList: List<MeetDetail> = emptyList(),
    navigateHome: () -> Unit = {},
    navigateMeet: (id: Int) -> Unit = {},
) {
    BackHandler {
        navigateHome()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeDrawingPadding()
    ) {
        Row {
            IconButton(
                onClick = navigateHome
            ) {
                Icon(painter = painterResource(R.drawable.icon_back), "close map place result")
            }
        }
        Text(
            text = "장소를 공유할 모임을 선택해 주세요.",
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold
        )
    }
}