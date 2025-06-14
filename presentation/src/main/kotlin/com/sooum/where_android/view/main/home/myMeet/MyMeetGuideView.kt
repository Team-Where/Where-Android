package com.sooum.where_android.view.main.home.myMeet

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale900
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard

@Composable
fun MyMeetGuideView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Column(
        modifier = modifier,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
        ) {
            Text(
                text = "모임 추가 방법",
                modifier = Modifier.align(Alignment.Center),
                fontFamily = pretendard,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            listOf(
                "추가하고 싶은 모임을 등록해보세요." to R.drawable.guide_image_1,
                "모임 참석할 친구를 초대해보세요." to R.drawable.guide_image_2,
                "모임 장소를 참석하는 친구들과 함께 공유해보세요." to R.drawable.guide_image_3,
            ).forEachIndexed { index, (title, res) ->
                GuideCardItem(
                    order = index + 1,
                    title = title,
                    imageRes = res
                )
                if (index != 3) {
                    Spacer(Modifier.height(48.dp))
                }
            }
        }

    }
}

@Composable
private fun GuideCardItem(
    order: Int,
    title: String,
    @DrawableRes imageRes: Int = 0
) {
    Column(
        modifier = Modifier.width(350.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Primary600)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = order.toString(),
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Text(
                text = title,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = GrayScale900
            )
        }

        Spacer(
            Modifier.height(24.dp)
        )

        Image(
            painter = painterResource(imageRes),
            contentDescription = "guide_image"
        )
    }
}


@Composable
@Preview(showSystemUi = false, showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun MyMeetGuideViewPreview() {
    MyMeetGuideView()
}