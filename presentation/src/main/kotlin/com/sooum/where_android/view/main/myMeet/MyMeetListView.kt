package com.sooum.where_android.view.main.myMeet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.MeetDetail
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale300
import com.sooum.where_android.theme.GrayScale500
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CoverImage

@Composable
internal fun MyMeetListView(
    meetDetailList: List<MeetDetail>,
    navigationGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (meetDetailList.isEmpty()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.image_main_mymeet_none),
                    contentDescription = null,
                    modifier = Modifier
                        .width(81.45.dp)
                        .height(87.dp)
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.my_meet_no_meet),
                    fontFamily = pretendard,
                    color = GrayScale500,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(20.dp))

                OutlinedButton(
                    onClick = navigationGuide,
                    modifier = Modifier.height(40.dp),
                    border = BorderStroke(1.dp, GrayScale300)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = Primary600
                        )
                        Text(
                            text = stringResource(R.string.my_meet_no_meet_btn),
                            fontFamily = pretendard,
                            color = Primary600,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            items(meetDetailList) {
                MyMeetContentCard(it)
            }
        }
    }
}


@Composable
private fun MyMeetContentCard(
    meetDetail: MeetDetail
) {
    val size = 170.dp
    Column {
        meetDetail.CoverImage(
            size = size,
            radius = 10.dp
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = meetDetail.title,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = meetDetail.date,
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}


internal class MeetDetailParameterProvider() : PreviewParameterProvider<List<MeetDetail>> {
    override val values: Sequence<List<MeetDetail>>
        get() = sequenceOf(
            emptyList(),
            listOf(
                MeetDetail(
                    1,
                    "2024 연말파티\uD83E\uDD42",
                    "벌써 연말이다 신나게 놀아보장~~",
                    "https://s3-alpha-sig.figma.com/img/c8d6/dcd3/d0cf1a8b848a3b713165544ecf9c6479?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=krYvjufSoqEAyn8acYCSG~-QUOROkCxVJUZM0JjokvSbO2Tcp9ukcsdTS0jCIhgFtpnODglNx-S-djkLy7DLJTwmX7gYCwEixyFT71peeBIssSulRl0~dMmtr8LPjmfPHAw2uADh7e~8WZJELBuE6gultPGoNSBFhEYdIXXoLgRscwHeJgwBTBjOYFf8N9pIQSwmSP-OsBdz9~LZQUKX1CisOb8yJtTx8SrPapdSMXYNickk~zQ7PaqfAeAXxyieTIGxSlNjp8QYzQhQrWcXkAFM9Y2xfNiArxvrJIKX-XykeplJAAIcwZ6U25H3UxA6F37LN7dBh1TjXr3VEwxEmA__",
                    2025,
                    1,
                    26
                ),
                MeetDetail(
                    2,
                    "행궁동 갈 사람\uD83C\uDF42",
                    "선선해진 날씨에 같이 사람~!",
                    "https://s3-alpha-sig.figma.com/img/aadb/e842/9b7456a077b223a1fdd7ce6ce4e4c046?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=bdatUU4QM18aBwb8waNdC4gX2mo2eT7J3FQM3Mysof0N624upN~0lo8BDm8p5WizzgOqcEFdjmG0mcUs8XBjGW7nXTo4aGiJOWyketQx8hOQlSoA3fJlnIM9fXyela2EhHrYWAJFZFAst~gc8Ox4xoGqc78iZ09hF9PDlWRMlMIME-WYycm9wBpcdauRS90mmCDX5CBoeawZqixOc~qfCBay5FE4~h~wc4vL89RKzoIAjOqPC14hL-ezdlr5SN~WDlWfN~l1a397hIYYsk-ytIw6BLTm~aOqnHKsPpzpXrHXoSkQR4AD9Rlnhoxpi4qNxtV2NUwQmM0Db3hioP7dkg__",
                    2024,
                    12,
                    11
                ),
                MeetDetail(
                    3,
                    "행궁동 갈 사람\uD83C\uDF42",
                    "선선해진 날씨에 같이 사람~!",
                    "",
                    2025,
                    1,
                    27
                )
            )
        )
}

@Composable
@Preview(
    backgroundColor = 0xFFFFFFFF, showSystemUi = true, showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=320"
)
private fun MyMeetListViewPreView(
    @PreviewParameter(MeetDetailParameterProvider::class) data: List<MeetDetail>
) {
    Surface(
        color = Color.White
    ) {
        MyMeetListView(
            meetDetailList = data,
            navigationGuide = {},
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
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
            MeetDetail(
                3,
                "행궁동 갈 사람\uD83C\uDF42",
                "선선해진 날씨에 같이 사람~!",
                "",
                2025,
                1,
                27
            )
        )
    }
}