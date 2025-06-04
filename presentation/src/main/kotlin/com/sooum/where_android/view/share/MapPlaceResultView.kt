package com.sooum.where_android.view.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.theme.pretendard

@Composable
fun MapPlaceResultView(
    meetDetailList: List<MeetDetail>,
    onBack: () -> Unit
) {
    Column {
        Row {
            IconButton(
                onClick = onBack
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