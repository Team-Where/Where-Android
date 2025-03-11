package com.sooum.where_android.view.main.meetDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray600
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CircleProfileView
import com.sooum.where_android.viewmodel.MeetDetailViewModel

@Composable
fun MeetDetailView(
    meetDetailViewModel: MeetDetailViewModel = hiltViewModel(),
    navigationMeetDetail: (MeetDetail) -> Unit,
    onBack: () -> Unit
) {
    val meetDetailList by meetDetailViewModel.meetDetailGroupList.collectAsState()
    MeetDetailContent(
        onBack = onBack,
        navigationMeetDetail = navigationMeetDetail,
        friend = meetDetailViewModel.getUserData()!!,
        meetDetailGroupList = meetDetailList
    )
}

@Composable
private fun MeetDetailContent(
    onBack: () -> Unit,
    navigationMeetDetail: (MeetDetail) -> Unit,
    friend: User,
    meetDetailGroupList: Map<Int, List<MeetDetail>>,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(
                    vertical = 5.dp
                ),
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = null,
                    tint = Gray800
                )
            }

            Row(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(R.string.meet_detail_title),
                    color = Gray800,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 프로필 헤더
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                ) {
                    val xOffset = 45.dp
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.TopCenter)
                                .offset(x = -xOffset)
                        ) {
                            MeetDetailProfileImage(
                                profileUrl = "https://s3-alpha-sig.figma.com/img/9483/f88f/f5cb8c568739dce8dfccbd21052d5203?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=c2C9C3hRNYGTpTmc2607-BjDjlAsK5bACRu2X-UJ35KrgsJqjPTvJqFzbVYM-7FiNtlB7eO5d~yf3uM7VlXSQ1sxAN0rAIc8SB2wuYh48Z3s7SIdr8WtuRH0MGB7IjrPDLWS-UnBRyQ7joM~qzCev43D~iS1dHpA~vZn7jPe9kIYFaAs4v7EKSDUX3zo9ZaaRJxnLjd8GQ1B7dnAJOUVPF~TYjA1cpGIPfhcBwp5WM~uq5uC43ZtDcLE-baAU6k5VXjxlSRn9-av7GC8iP-yDcf94x5aLHOAjhtRcUCJZ9I5hsgt2VjLMxv4-m7dkK1hix63uAEuVo9awUC0RykU8g__",
                                useBorder = false,
                                name = "나"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.TopCenter)
                                .offset(x = xOffset)
                        ) {
                            MeetDetailProfileImage(
                                profileUrl = friend.profileImage,
                                name = friend.name,
                                useBorder = true
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 20.dp)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.meet_detail_info_with_friend,
                                friend.name
                            ),
                            color = Gray600,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            //구분 Spacer
            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Gray100)
                )
            }

            this.initGroupItem(
                meetDetailGroupList = meetDetailGroupList,
                navigationMeetDetail = navigationMeetDetail
            )
        }

    }
}

private fun LazyListScope.initGroupItem(
    meetDetailGroupList: Map<Int, List<MeetDetail>>,
    navigationMeetDetail: (MeetDetail) -> Unit,
) {
    meetDetailGroupList.keys.forEachIndexed { yearIndex, year ->
        item {
            MeetDetailHeader(year, yearIndex == 0)
        }
        val meetDetailList = meetDetailGroupList[year]!!
        items(
            count = meetDetailList.size,
            key = {
                meetDetailList[it].id
            }
        ) { index ->
            val prevItem = kotlin.runCatching { meetDetailList[index - 1] }.getOrNull()
            val meetDetail = meetDetailList[index]
            val nextItem = kotlin.runCatching { meetDetailList[index + 1] }.getOrNull()
            Row(
                Modifier.padding(horizontal = 20.dp)
            ) {
                val cardHeight = 153.dp
                val bottomSpace = if (index == meetDetailList.size - 1) {
                    20.dp
                } else {
                    20.dp
                }

                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(cardHeight + bottomSpace)
                ) {
//                            if (meetDetail.month != nextItem?.month) {
//                                VerticalDivider(
//                                    modifier = Modifier
//                                        .height(cardHeight + bottomSpace / 2)
//                                        .align(Alignment.TopCenter)
//                                )
//                            } else {
                    VerticalDivider(
                        modifier = Modifier
                            .height(cardHeight + bottomSpace)
                            .align(Alignment.TopCenter)
                    )
//                            }


                    if (
                        prevItem == null ||
                        (prevItem.month != meetDetail.month)
                    ) {
                        Column(
                            modifier = Modifier
                                .height(40.dp)
                                .background(Color.White)
                        ) {
                            Spacer(
                                Modifier.height(5.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(10.dp)
                                        .background(Color(0xffeef2ff))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(Primary600)
                                    )
                                }
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = stringResource(
                                        R.string.meet_detail_month,
                                        meetDetail.month
                                    ),
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                }
                MeetDetailCard(
                    meetDetail = meetDetail,
                    onClick = {
                        navigationMeetDetail(meetDetail)
                    }
                )
            }
        }
    }
}

@Composable
private fun MeetDetailHeader(
    year: Int,
    isFirstHeader: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = 20.dp
            ),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = year.toString(),
            fontWeight = FontWeight.SemiBold,
            fontFamily = pretendard,
            fontSize = 24.sp
        )


        if (isFirstHeader) {
            Text(
                text = stringResource(R.string.meet_detail_info_exp),
                fontWeight = FontWeight.Normal,
                fontFamily = pretendard,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun MeetDetailProfileImage(
    profileUrl: String,
    useBorder: Boolean,
    name: String
) {
    val profileSize = 100.dp
    val borderSize = 3.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(profileSize),
        verticalArrangement = Arrangement.Top
    ) {
        CircleProfileView(
            profileUrl = profileUrl,
            size = profileSize,
            modifier = Modifier
                .then(
                    if (!useBorder) {
                        Modifier
                    } else {
                        Modifier
                            .border(
                                borderSize,
                                Color.White,
                                RoundedCornerShape(profileSize / 2)
                            )
                    }
                )

        )
        Spacer(
            Modifier.height(8.dp)
        )
        Text(
            text = name,
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview(
    showBackground = true, showSystemUi = false,
    device = "id:Nexus 5"
)
private fun MeetDetailContentPreview() {
    Surface(
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.safeDrawingPadding(),
        ) {
            MeetDetailContent(
                onBack = {},
                navigationMeetDetail = {},
                friend = User(0, "우리동네 먹짱방방"),
                meetDetailGroupList = emptyMap()
            )
        }
    }
}


internal class GroupDataParameterProvider() : PreviewParameterProvider<Map<Int, List<MeetDetail>>> {
    override val values: Sequence<Map<Int, List<MeetDetail>>>
        get() = sequenceOf(
            mapOf(
                2025 to listOf(
                    MeetDetail(
                        3,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        "",
                        2025,
                        1,
                        27
                    ),
                    MeetDetail(
                        1,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        "",
                        2025,
                        1,
                        26
                    ),
                    MeetDetail(
                        4,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        "",
                        2025,
                        1,
                        25
                    )
                )
            ),
            mapOf(
                2025 to listOf(
                    MeetDetail(
                        3,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        "",
                        2025,
                        1,
                        27
                    ),
                    MeetDetail(
                        1,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        "",
                        2025,
                        1,
                        26
                    ),
                    MeetDetail(
                        4,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        "",
                        2025,
                        1,
                        25
                    )
                ),
                2024 to listOf(
                    MeetDetail(
                        2,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        "",
                        2024,
                        12,
                        25
                    ),
                )
            ),
            mapOf(
                2025 to listOf(
                    MeetDetail(
                        6,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        "",
                        2025,
                        2,
                        2
                    ),
                    MeetDetail(
                        7,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        "",
                        2025,
                        2,
                        1
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
                ),
                2024 to listOf(
                    MeetDetail(
                        2,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        "",
                        2024,
                        12,
                        25
                    ),
                )
            )
        )
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun GroupDataListViewPreview(
    @PreviewParameter(GroupDataParameterProvider::class) data: Map<Int, List<MeetDetail>>
) {
    Surface(
        color = Color.White,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            initGroupItem(data, {})
        }
    }
}