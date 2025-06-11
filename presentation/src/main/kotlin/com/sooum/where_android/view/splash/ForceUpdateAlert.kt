package com.sooum.where_android.view.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.openMarket
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.PrimaryButton

@Composable
fun ForceUpdateView() {
    val context = LocalContext.current

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
            text = "업데이트 필요",
            fontFamily = pretendard,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "새로운 버전이 출시되었습니다.\n최신 버전으로 업데이트 후 이용해 주세요.",
            fontFamily = pretendard,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                context.openMarket()
            },
            title = "업데이트"
        )
    }
}

@Composable
@Preview
private fun ForceUpdateViewPreview() {
    ForceUpdateView()
}