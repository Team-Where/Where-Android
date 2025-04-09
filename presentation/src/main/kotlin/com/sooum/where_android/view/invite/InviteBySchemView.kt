package com.sooum.where_android.view.invite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.SimpleMeet
import com.sooum.where_android.theme.Gray200
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CoverImage
import com.sooum.where_android.view.widget.PrimaryButton

@Composable
fun InviteBySchemeView(
    simpleMeet: SimpleMeet
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(386.dp)
                .align(Alignment.Center)
                .border(1.dp, Gray200, RoundedCornerShape(16.dp))
                .padding(horizontal = 91.dp)
                .padding(vertical = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            simpleMeet.CoverImage(120.dp, 16.dp)
            Spacer(Modifier.height(16.dp))
            Text(
                text = simpleMeet.title,
                fontSize = 24.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                color = GrayScale900
            )
            Spacer(Modifier.height(12.dp))
            Text("${simpleMeet.date} + ${simpleMeet.time}")
            Spacer(Modifier.height(24.dp))
            PrimaryButton(
                onClick = {},
                title = "수락하기",
                modifier = Modifier.width(103.dp),
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun InviteSchemePreview() {
    InviteBySchemeView(
        SimpleMeet(
            0,
            "2024 연말파티\uD83E\uDD42",
            "",
            "",
            ""
        )
    )
}