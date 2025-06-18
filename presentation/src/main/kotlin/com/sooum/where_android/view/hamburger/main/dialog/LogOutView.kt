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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.GrayButton
import com.sooum.where_android.view.widget.PrimaryButton

@Composable
fun LogOutView(
    dismiss: () -> Unit = {},
    logOut: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "정말 로그아웃 하시겠습니까?",
            fontFamily = pretendard,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrayButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = dismiss,
                title = "취소"
            )
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = logOut,
                title = "로그아웃"
            )
        }
    }
}