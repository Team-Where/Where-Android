package com.sooum.where_android.view.main.home.friendList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.Friend
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray300
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.GrayScale800
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CoverImageView

@Composable
fun FriendMeetDetailCard(
    meetDetail: Friend.FriendMeet,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .height(153.dp)
            .background(Color(0xffeef2ff))
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            CoverImageView(
                src = meetDetail.image,
                size = 65.dp,
                radius = 12.dp
            )
            Spacer(
                Modifier.width(16.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = with(meetDetail.date) {
                        stringResource(
                            R.string.friend_meet_detail_card_date,
                            year,
                            month,
                            day
                        )
                    },
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Gray500
                )
                Text(
                    text = meetDetail.name,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xff111827)
                )
                Text(
                    text = meetDetail.description,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Gray500
                )
            }
        }

        TextButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Gray300),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.meet_detail_show),
                color = GrayScale800,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
@Preview
fun MeetDetailCardPreview() {
    FriendMeetDetailCard(
        Friend.FriendMeet(
            1,
            null,
            "2024 연말파티\uD83E\uDD42",
            "벌써 연말이다 신나게 놀아보장~~",
            Friend.Date("2025-01-29")
        ),
        modifier = Modifier.fillMaxWidth(),
        onClick = {}
    )
}