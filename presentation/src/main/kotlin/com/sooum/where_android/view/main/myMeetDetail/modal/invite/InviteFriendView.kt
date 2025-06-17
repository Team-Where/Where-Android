package com.sooum.where_android.view.main.myMeetDetail.modal.invite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sooum.domain.model.Friend
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.toUser
import com.sooum.domain.usecase.user.GetLoginUserUseCase
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.Gray600
import com.sooum.where_android.theme.Gray700
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.util.KaKaoShareUtil
import com.sooum.where_android.view.widget.CircleProfileView
import com.sooum.where_android.view.widget.SearchField
import com.sooum.where_android.view.widget.UserItemView
import com.sooum.where_android.view.widget.UserViewType
import com.sooum.where_android.viewmodel.meetdetail.MyMeetDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun InviteFriendView(
    modifier: Modifier = Modifier,
    inviteFriendList: List<InvitedFriend>,
    waitingFriendList: List<InvitedFriend>,
    userList: List<Friend>,
    recentUserList: List<Friend>,
    inviteByKaKao: () -> Unit,
    inviteFriend: (Friend) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            var searchingMode by remember {
                mutableStateOf(false)
            }
            var searchValue by remember {
                mutableStateOf("")
            }
            BackHandler(searchingMode) {
                searchValue = ""
                searchingMode = false
            }
            val searchFocusRequester = remember { FocusRequester() }
            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = !searchingMode,
                    enter =
                        slideInHorizontally(animationSpec = tween(durationMillis = 500)) { fullWidth ->
                            -fullWidth / 3
                        } + fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = slideOutHorizontally(animationSpec = tween(300)) + fadeOut(),
                    modifier = Modifier.align(Alignment.Center)
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
                            onClick = {
                                searchingMode = true
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(Icons.Filled.Search, null)
                        }
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = searchingMode,
                    enter =
                        slideInHorizontally(animationSpec = tween(durationMillis = 500)) { fullWidth ->
                            fullWidth / 3
                        } + fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = slideOutHorizontally(animationSpec = tween(300)) + fadeOut(),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    LaunchedEffect(true) {
                        searchFocusRequester.requestFocus()
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        SearchField(
                            searchValue,
                            onValueChange = {
                                searchValue = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 50.dp)
                                .focusRequester(searchFocusRequester)
                                .align(Alignment.CenterStart),
                            placeHolder = "검색"
                        )
                        TextButton(
                            onClick = {
                                searchFocusRequester.freeFocus()
                                searchValue = ""
                                searchingMode = false
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .width(50.dp)
                        ) {
                            Text("취소")
                        }
                    }
                }
            }
            if (searchingMode) {
                if (searchValue.isEmpty()) {
                    Spacer(Modifier.fillMaxSize())
                } else {
                    val filterNameList = userList.filter { it.name.contains(searchValue) }
                    if (filterNameList.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .imePadding()
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.friend_list_search_no_data),
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Gray700
                            )
                        }
                    } else {
                        LazyColumn() {
                            items(
                                items = filterNameList,
                                key = {
                                    "df_f" + it.id
                                }
                            ) { friend ->
                                UserItemView(
                                    user = friend.toUser(),
                                    type = UserViewType.Invite,
                                )
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    InviteFriendHeader(
                        invitedFriendList = inviteFriendList,
                        waitingFriendList = waitingFriendList
                    )
                    InviteFriendContentView(
                        recentUserList = recentUserList,
                        userList = userList,
                        inviteFriend = inviteFriend,
                        headerColor = Gray600,
                        kakaoClickAction = inviteByKaKao
                    )
                }
            }
        }
    }
}

@Composable
private fun InviteFriendHeader(
    invitedFriendList: List<InvitedFriend>,
    waitingFriendList: List<InvitedFriend>
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
                    text = stringResource(R.string.invite_friend_header, invitedFriendList.size),
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
                            waitingFriendList.size,
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
                    items = invitedFriendList
                ) { user ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircleProfileView(
                            profileUrl = user.image,
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
                    items = waitingFriendList
                ) { user ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .alpha(0.5f)
                    ) {
                        CircleProfileView(
                            profileUrl = user.image,
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
        inviteFriendList = emptyList(),
        waitingFriendList = emptyList(),
        userList = emptyList(),
        recentUserList = emptyList(),
        inviteFriend = {},
        inviteByKaKao = {}
    )
}


@AndroidEntryPoint
class InviteFriendFragment : Fragment() {

    private val myMeetDetailViewModel: MyMeetDetailViewModel by activityViewModels()

    @Inject
    lateinit var getLoginUserUseCase: GetLoginUserUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
                val inviteFriendList by myMeetDetailViewModel.invitedFriendList.collectAsState(
                    emptyList()
                )
                val waitingFriendList by myMeetDetailViewModel.waitingFriendList.collectAsState(
                    emptyList()
                )
                InviteFriendView(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .navigationBarsPadding()
                        .padding(10.dp),
                    inviteFriendList = inviteFriendList,
                    waitingFriendList = waitingFriendList,
                    userList = emptyList(),
                    recentUserList = emptyList(),
                    inviteFriend = {},
                    inviteByKaKao = {
                        scope.launch {
                            myMeetDetailViewModel.meetDetail.value?.let { meet ->
                                Log.d("JWH", meet.toString())
                                //로그인된 화면이므로 getLoginUserUseCase는 항상 null이 아니다.
                                KaKaoShareUtil.sendInvite(
                                    context = context,
                                    userName = getLoginUserUseCase().first()!!.nickname,
                                    meetName = meet.title,
                                    inviteCode = meet.inviteCode
                                )
                            }
                        }
                    },
                    onBack = {
                        findNavController().popBackStack()
                    }
                )
            }
        }
    }
}