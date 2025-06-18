package com.sooum.where_android.view.main.home.friendList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
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
import com.sooum.domain.model.Friend
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray600
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CircleProfileView
import com.sooum.where_android.viewmodel.main.FriendDetailViewModel

@Composable
fun FriendMeetDetailView(
    friendDetailViewModel: FriendDetailViewModel = hiltViewModel(),
    navigationMeetDetail: (Friend.FriendMeet) -> Unit,
    onBack: () -> Unit
) {
    val friend by friendDetailViewModel.friend.collectAsState()
    val myProfile by friendDetailViewModel.userProfile.collectAsState()

    BackHandler {
        onBack()
    }
    MeetDetailContent(
        onBack = onBack,
        navigationMeetDetail = navigationMeetDetail,
        friend = friend,
        userProfile = myProfile
    )
}

@Composable
private fun MeetDetailContent(
    onBack: () -> Unit,
    navigationMeetDetail: (Friend.FriendMeet) -> Unit,
    friend: Friend?,
    userProfile: String
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    if (friend != null) {
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
                                    profileUrl = userProfile,
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
                                    profileUrl = friend.image ?: "",
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
                if (friend.meetList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(
                                    with(LocalDensity.current) {
                                        screenHeight -
                                                300.dp - // 프로필 영역
                                                8.dp - //SpacerBar
                                                75.dp - // bottom
                                                64.dp - //현재 헤더 영역
                                                WindowInsets.statusBars.getTop(this)
                                                    .toDp() -  // 상태바
                                                WindowInsets.navigationBars.getBottom(this)
                                                    .toDp() // 네비바
                                    }
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_info),
                                    contentDescription = null,
                                    tint = GrayScale600
                                )
                                Text(
                                    text = "아직 함께하는 모임이 없어요!",
                                    fontFamily = pretendard,
                                    color = GrayScale700,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                } else {
                    this.initGroupItem(
                        meetDetailGroupList = friend.meetList.groupBy { it.date.year },
                        navigationMeetDetail = navigationMeetDetail
                    )
                }
            }

        }
    }
}

private fun LazyListScope.initGroupItem(
    meetDetailGroupList: Map<Int, List<Friend.FriendMeet>>,
    navigationMeetDetail: (Friend.FriendMeet) -> Unit,
) {
    meetDetailGroupList.keys.forEachIndexed { yearIndex, year ->
        item {
            MeetDetailHeader(year, yearIndex == 0)
        }
        val meetDetailList = meetDetailGroupList[year]!!
        items(
            count = meetDetailList.size,
            key = {
                meetDetailList[it].meetId
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
                    VerticalDivider(
                        modifier = Modifier
                            .height(cardHeight + bottomSpace)
                            .align(Alignment.TopCenter)
                    )



                    if (
                        prevItem == null ||
                        (prevItem.date.month != meetDetail.date.month)
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
                                    text = meetDetail.date.month.let {
                                        stringResource(
                                            R.string.meet_detail_month,
                                            it
                                        )
                                    },
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                }
                FriendMeetDetailCard(
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
                friend = Friend(0, "우리동네 먹짱방방"),
                userProfile = ""
            )
        }
    }
}


internal class GroupDataParameterProvider() :
    PreviewParameterProvider<Map<Int, List<Friend.FriendMeet>>> {
    override val values: Sequence<Map<Int, List<Friend.FriendMeet>>>
        get() = sequenceOf(
            mapOf(
                2025 to listOf(
                    Friend.FriendMeet(
                        3,
                        null,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        Friend.Date("2025-01-27")
                    ),
                    Friend.FriendMeet(
                        1,
                        null,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        Friend.Date("2025-01-26")
                    ),
                    Friend.FriendMeet(
                        4,
                        null,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        Friend.Date("2025-01-25")
                    )
                )
            ),
            mapOf(
                2025 to listOf(
                    Friend.FriendMeet(
                        3,
                        null,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        Friend.Date("2025-01-27")
                    ),
                    Friend.FriendMeet(
                        1,
                        null,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        Friend.Date("2025-01-26")
                    ),
                    Friend.FriendMeet(
                        4,
                        null,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        Friend.Date("2025-01-25")
                    )
                ),
                2024 to listOf(
                    Friend.FriendMeet(
                        2,
                        null,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        Friend.Date("2024-12-25")
                    ),
                )
            ),
            mapOf(
                2025 to listOf(
                    Friend.FriendMeet(
                        6,
                        null,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        Friend.Date("2025-02-02")
                    ),
                    Friend.FriendMeet(
                        7,
                        null,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        Friend.Date("2025-02-01")
                    ),
                    Friend.FriendMeet(
                        3,
                        null,
                        "행궁동 갈 사람\uD83C\uDF42",
                        "선선해진 날씨에 같이 사람~!",
                        Friend.Date("2025-01-27")
                    )
                ),
                2024 to listOf(
                    Friend.FriendMeet(
                        2,
                        null,
                        "2024 연말파티\uD83E\uDD42",
                        "벌써 연말이다 신나게 놀아보장~~",
                        Friend.Date("2024-12-25")
                    ),
                )
            )
        )
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun GroupDataListViewPreview(
    @PreviewParameter(GroupDataParameterProvider::class) data: Map<Int, List<Friend.FriendMeet>>
) {
    Surface(
        color = Color.White,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            initGroupItem(
                meetDetailGroupList = data,
                navigationMeetDetail = {}
            )
        }
    }
}