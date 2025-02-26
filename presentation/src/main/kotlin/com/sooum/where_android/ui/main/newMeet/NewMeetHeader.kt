package com.sooum.where_android.ui.main.newMeet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.viewmodel.NewMeetType

@Composable
fun NewMeetHeader(
    type: NewMeetType
) {
    Column {
        Text(
            text = "새 모임 만들기(${type.order}/2)",
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Primary600
        )
        Spacer(
            Modifier.height(16.dp)
        )
        Text(
            text = type.title,
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}