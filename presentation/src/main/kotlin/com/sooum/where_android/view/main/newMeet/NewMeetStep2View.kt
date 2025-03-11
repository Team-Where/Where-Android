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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import com.sooum.where_android.ui.invite.InviteFriendContentView
import com.sooum.where_android.ui.widget.PrimaryButton
import com.sooum.where_android.viewmodel.NewMeetType
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewMeetStep2View(
    modifier: Modifier = Modifier,
    userList: List<User>,
    recentUserList: List<User>,
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
                PrimaryButton(
                    onClick = nextViewType,
                    title = NewMeetType.Friend.buttonTitle
                )
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
                InviteFriendContentView(
                    recentUserList = recentUserList,
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
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun NewMeetStep2ViewPreview() {
    NewMeetStep2View(
        recentUserList = emptyList(),
        userList = emptyList(),
        inviteFriend = {},
        nextViewType = {},
        onClose = {}
    )
}