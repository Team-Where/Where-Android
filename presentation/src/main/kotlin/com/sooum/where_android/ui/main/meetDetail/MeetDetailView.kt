package com.sooum.where_android.ui.main.meetDetail

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray600
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.ui.widget.CircleProfileView
import com.sooum.where_android.viewmodel.MeetDetailViewModel

@Composable
fun MeetDetailView(
    meetDetailViewModel: MeetDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    MeetDetailContent(
        onBack = onBack,
        friend = meetDetailViewModel.getUserData()!!
    )
}

@Composable
private fun MeetDetailContent(
    onBack: () -> Unit,
    friend: User
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
                    text = "모임활동",
                    color = Gray800,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            ) {
                val xOffset = 45.dp
                Box(
                    modifier = Modifier.wrapContentSize()
                        .align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.TopCenter)
                            .offset(x = -xOffset)
                    ) {
                        MeetDetailProfileImage(
                            profileUrl = "",
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
                        text = "냠냠쩝쩝님과 함께한 모임을 확인해보세요.",
                        color = Gray600,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(
                Modifier.fillMaxWidth()
                    .height(8.dp)
                    .background(Gray100)
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
@Preview(showBackground = true, showSystemUi = true)
private fun MeetDetailContentPreview() {
    Column(
        modifier = Modifier.safeDrawingPadding()
    ) {
        MeetDetailContent(
            onBack = {},
            friend = User(0,"우리동네 먹짱방방")
        )

    }
}