package com.sooum.where_android.view.main.myMeetDetail.modal.invite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.Gray600
import com.sooum.where_android.theme.Gray700
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.CircleProfileView

@Composable
fun InviteFriendView(
    userList: List<User>,
    recentUserList: List<User>,
    inviteFriend: (User) -> Unit,
    onBack: () -> Unit
) {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
                Text(
                    text = "친구 초대",
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = pretendard,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(Icons.Filled.Search, null)
                }
            }
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                InviteFriendHeader(
                    invitedUser = listOf(
                        User(0, "hi"),
                        User(1, "hi2"),
                        User(2, "hi3")
                        ),
                    notInvitedUser = listOf(
                        User(3, "sad"),
                        User(4, "sad2"),
                        User(5, "sad3"),
                    ),
                )
                InviteFriendContentView(
                    recentUserList = recentUserList,
                    userList = userList,
                    inviteFriend = inviteFriend,
                    headerColor = Gray600
                )
            }
        }
    }
}

@Composable
private fun InviteFriendHeader(
    invitedUser: List<User>,
    notInvitedUser: List<User>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Gray100, RoundedCornerShape(16.dp))
            .shadow(1.dp, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(25.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.invite_friend_header, invitedUser.size),
                    fontFamily = pretendard,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray800
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(26.dp))
                        .background(Gray100)
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                ) {
                    Text(
                        text = stringResource(
                            R.string.invite_friend_header_waiting,
                            notInvitedUser.size,
                        ),
                        fontFamily = pretendard,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Gray500
                    )
                }
            }
            HorizontalDivider(
                color = Gray100
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(
                    items = notInvitedUser
                ) { user ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircleProfileView(
                            profileUrl = user.profileImage,
                            size = 40.dp,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = user.name,
                            fontFamily = pretendard,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Gray700
                        )
                    }
                }
                items(
                    items = notInvitedUser
                ) { user ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .alpha(0.5f)
                    ) {
                        CircleProfileView(
                            profileUrl = user.profileImage,
                            size = 40.dp,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = user.name,
                            fontFamily = pretendard,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Gray700
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true, showSystemUi = true, backgroundColor = 0xFFFFFFFF,
    device = "spec:width=411dp,height=891dp"
)
private fun InviteFriendViewPreview() {
    InviteFriendView(
        onBack = {},
        userList = emptyList(),
        recentUserList = emptyList(),
        inviteFriend = {}
    )
}