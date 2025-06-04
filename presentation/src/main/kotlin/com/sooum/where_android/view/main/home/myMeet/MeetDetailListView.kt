package com.sooum.where_android.view.main.home.myMeet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.Schedule
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CoverImageView

@Composable
fun MeetDetailListView(
    meetDetailList: List<MeetDetail>,
    navigationMeetDetail: (MeetDetail) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(meetDetailList) { meetDetail ->
            MyMeetContentCard(
                meetDetail = meetDetail,
                onClick = {
                    navigationMeetDetail(meetDetail)
                }
            )
        }
    }
}

@Composable
private fun MyMeetContentCard(
    meetDetail: MeetDetail,
    onClick: () -> Unit
) {
    val size = 170.dp
    Column(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally)
        ) {
            CoverImageView(
                src = meetDetail.image,
                size = size,
                radius = 10.dp
            )
            if (meetDetail.finished) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .background(Primary600, RoundedCornerShape(17.dp))
                            .width(65.dp)
                            .height(21.dp)
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = "종료된 모임",
                            color = Color.White,
                            fontFamily = pretendard,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = meetDetail.title,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = if (meetDetail.finished) {
                Color.Black.copy(0.5f)
            } else {
                Color.Black
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = meetDetail.date ?: "등록된 일정이 없어요",
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = if (meetDetail.finished) {
                Color.Black.copy(0.25f)
            } else {
                Color.Black.copy(0.5f)
            }
        )
    }
}


@Composable
@Preview
private fun MyMeetContentCardPreView() {
    Surface(
        color = Color.White
    ) {
        MyMeetContentCard(
            meetDetail = MeetDetail(
                3,
                "행궁동 갈 사람\uD83C\uDF42",
                "선선해진 날씨에 같이 사람~!",
                Schedule(3, "2025-1-27", "")
            ),
            onClick = {}
        )
    }
}