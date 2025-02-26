package com.sooum.where_android.ui.main.newMeet

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale100
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.ui.main.NewMeetResult
import com.sooum.where_android.ui.widget.CoverImageView
import com.sooum.where_android.ui.widget.PrimaryButton

@Composable
fun NewMeetResultView(
    result: NewMeetResult,
    close: () -> Unit = {},
    navigationDetail: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = close
            ) {
                Icon(Icons.Filled.Clear, null)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "새 모임이 생성되었어요.",
                color = Primary600,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                text = result.title,
                color = GrayScale900,
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            )
            CoverImageView(
                src = result.image,
                size = 154.dp,
                radius = 16.dp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .height(60.dp)
                    .background(GrayScale100)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_new_meet_result),
                    contentDescription = null
                )
                Text(
                    text = "친구들과 자유롭게 장소를 공유해보세요.",
                    color = GrayScale700,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
            PrimaryButton(
                onClick = navigationDetail,
                title = "모임방으로 이동"
            )
        }
    }
}

@Composable
@Preview
fun NewMeetResultPreview() {
    NewMeetResultView(
        NewMeetResult(
            "2024 연말파티\uD83E\uDD42",
            null
        )
    )
}