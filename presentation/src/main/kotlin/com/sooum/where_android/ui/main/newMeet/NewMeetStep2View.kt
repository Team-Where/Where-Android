package com.sooum.where_android.ui.main.newMeet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.SnackBarColor
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.ui.widget.UserItemView
import com.sooum.where_android.ui.widget.UserViewType
import com.sooum.where_android.viewmodel.NewMeetType
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewMeetStep2View(
    modifier: Modifier = Modifier,
    userList: List<User>,
    nextViewType: () -> Unit,
    inviteFriend: (User) -> Unit,
    onClose: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .padding(horizontal = 10.dp)
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SnackBarColor)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_new_meet_check),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Primary600
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = data.visuals.message,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 10.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
            ) {
                Button(
                    onClick = nextViewType,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary600
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = NewMeetType.Friend.buttonTitle,
                        fontFamily = pretendard,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onClose,
                    ) {
                        Icon(Icons.Filled.Clear, null)
                    }
                }
                var cancelJob: Job? by remember {
                    mutableStateOf(null)
                }
                NewMeetHeader(type = NewMeetType.Friend)
                NewMeetStep2ViewContent(
                    recentUserList = userList,
                    userList = userList,
                    inviteFriend = { user ->
                        scope.launch {
                            cancelJob?.cancel()
                            async {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    "\'${user.name}\'님을 초대 했습니다."
                                )
                            }
                            cancelJob = async {
                                delay(2000L)
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                            inviteFriend(user)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NewMeetStep2ViewContent(
    recentUserList: List<User>,
    userList: List<User>,
    inviteFriend: (User) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp,
                bottom = 20.dp
            )
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffF9E000)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Icon(
                    painterResource(
                        R.drawable.image_kakao_icon,
                    ),
                    null
                )
                Text(
                    text = "카톡으로 초대하기",
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                Text(
                    text = "최근 만난 친구",
                    modifier = Modifier.height(20.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard,
                    fontSize = 14.sp
                )
            }
            items(
                items = recentUserList,
                key = {
                    "rc" + it.id
                }
            ) { user ->
                UserItemView(
                    user = user,
                    type = UserViewType.Invite,
                    iconClickAction = {
                        inviteFriend(user)
                    }
                )
            }
            item {
                Text(
                    text = "친구(${userList.size})",
                    modifier = Modifier.height(20.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard,
                    fontSize = 14.sp
                )
            }
            items(
                items = userList,
                key = {
                    "d" + it.id
                }
            ) { user ->
                UserItemView(
                    user = user,
                    type = UserViewType.Invite,
                    iconClickAction = {
                        inviteFriend(user)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun NewMeetStep2ViewPreview() {
    NewMeetStep2ViewContent(
        recentUserList = emptyList(),
        userList = emptyList(),
        inviteFriend = {}
    )
}