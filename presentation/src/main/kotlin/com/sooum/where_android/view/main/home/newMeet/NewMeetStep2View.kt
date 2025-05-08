package com.sooum.where_android.view.main.home.newMeet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sooum.domain.model.Friend
import com.sooum.where_android.view.main.myMeetDetail.modal.invite.InviteFriendContentView
import com.sooum.where_android.view.widget.IconType
import com.sooum.where_android.view.widget.PrimaryButton
import com.sooum.where_android.view.widget.SnackBarContent
import com.sooum.where_android.viewmodel.NewMeetType
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewMeetStep2View(
    modifier: Modifier = Modifier,
    userList: List<Friend>,
    recentUserList: List<Friend>,
    nextViewType: () -> Unit,
    inviteFriend: (Friend) -> Unit,
    onClose: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                SnackBarContent(
                    message = data.visuals.message,
                    iconType = IconType.Check
                )
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