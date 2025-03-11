package com.sooum.where_android.view.main.friendList

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.model.ScreenRoute
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray400
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.Gray700
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.main.friendList.modal.DeleteUserModal
import com.sooum.where_android.view.main.friendList.modal.ProfileDetailModal
import com.sooum.where_android.view.widget.SearchField
import com.sooum.where_android.view.widget.UserItemView
import com.sooum.where_android.view.widget.UserViewType
import com.sooum.where_android.viewmodel.UserViewModel


sealed class FriendListViewType(
    @StringRes val title: Int,
    val fontSize: Int,
    @StringRes val buttonTitle: Int
) {
    data object Default : FriendListViewType(
        title = R.string.friend_list_title,
        fontSize = 24,
        buttonTitle = R.string.friend_list_edit_btn
    )

    data object Edit : FriendListViewType(
        title = R.string.friend_list_edit_title,
        fontSize = 16,
        buttonTitle = R.string.friend_list_edit_btn_end
    )
}

@Composable
fun FriendListView(
    userViewModel: UserViewModel = hiltViewModel(),
    navigationMeetDetail: (ScreenRoute.Home.FriendMeetDetail) -> Unit,
    modifier: Modifier
) {
    val userList by userViewModel.userList.collectAsState()
    FriedListContent(
        userList = userList,
        navigationMeetDetail = navigationMeetDetail,
        deleteUser = userViewModel::deleteUser,
        updateFavorite = userViewModel::updateUserFavorite,
        modifier = modifier
    )
}

@Composable
private fun FriedListContent(
    userList: List<User>,
    navigationMeetDetail: (ScreenRoute.Home.FriendMeetDetail) -> Unit,
    deleteUser: (id: Long) -> Unit,
    updateFavorite: (id: Long, favorite: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var viewType: FriendListViewType by remember {
        mutableStateOf(FriendListViewType.Default)
    }

    Column(
        modifier = modifier
    ) {
        var searchValue by remember {
            mutableStateOf("")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(
                    vertical = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    viewType == FriendListViewType.Edit
                ) {
                    IconButton(
                        onClick = {
                            viewType = FriendListViewType.Default
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_back),
                            contentDescription = null,
                            tint = Gray800
                        )
                    }
                }
                Text(
                    text = stringResource(viewType.title),
                    color = Gray800,
                    fontSize = viewType.fontSize.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            }

            TextButton(
                onClick = {
                    viewType = when (viewType) {
                        FriendListViewType.Default -> {
                            FriendListViewType.Edit
                        }

                        FriendListViewType.Edit -> {
                            FriendListViewType.Default
                        }
                    }
                },
                enabled = userList.isNotEmpty()
            ) {
                Text(
                    text = stringResource(viewType.buttonTitle),
                    color = if (userList.isEmpty()) Color.Gray else Primary600,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
            }
        }
        SearchField(
            searchValue,
            onValueChange = {
                searchValue = it
            },
            modifier =  Modifier.fillMaxWidth(),
            placeHolder = stringResource(R.string.friend_list_search_placeholder)
        )
        Spacer(
            Modifier.height(
                height = 21.dp
            )
        )
        if (userList.isEmpty()) {
            //아에 정보가 없는 경우
            Column {
                UserListHeader(
                    title = stringResource(R.string.friend_list_item_header_friend, userList.size)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(R.drawable.icon_warning),
                        contentDescription = "",
                        tint = GrayScale600
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = stringResource(R.string.friend_list_info_no_data),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = GrayScale700
                    )
                }
            }
        } else {

            //검색 값을 가져옴
            val filterValue = searchValue.trim()

            if (filterValue.isEmpty()) {
                //검색값이 없는 경우

                val favoriteUserList = userList.filter { it.isFavorite }
                var selectedUserId: Long? by remember {
                    mutableStateOf(null)
                }
                val favoriteEnter =
                    slideInVertically() + expandVertically() + fadeIn()

                val favoriteExit =
                    slideOutVertically() + shrinkVertically() + fadeOut()
                LazyColumn() {
                    //즐겨 찾기는 기본 모드에서만(검색값이 없을때만)
                    item {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = viewType == FriendListViewType.Default,
                            enter = favoriteEnter,
                            exit = favoriteExit
                        ) {
                            UserListHeader(
                                title = stringResource(R.string.friend_list_item_header_favorite_friend, favoriteUserList.size)
                            )
                        }
                    }
                    items(
                        items = favoriteUserList,
                        key = {
                            "fv" + it.id
                        }
                    ) { user ->
                        androidx.compose.animation.AnimatedVisibility(
                            visible = viewType == FriendListViewType.Default,
                            enter = favoriteEnter,
                            exit = favoriteExit
                        ) {
                            UserItemView(
                                user = user,
                                type = UserViewType.Favorite(user.isFavorite),
                                userClickAction = {
                                    selectedUserId = user.id
                                },
                                iconClickAction = {
                                    updateFavorite(user.id, !user.isFavorite)
                                }
                            )
                        }
                    }
                    item {
                        UserListHeader(
                            title = stringResource(R.string.friend_list_item_header_friend, userList.size)
                        )
                    }
                    items(
                        items = userList,
                        key = {
                            "df" + it.id
                        }
                    ) { user ->
                        UserItemViewByListView(
                            user = user,
                            viewType = viewType,
                            deleteUser = deleteUser,
                            updateFavorite = updateFavorite,
                            userClickAction = if (viewType == FriendListViewType.Default) {
                                {
                                    selectedUserId = user.id
                                }
                            } else {
                                null
                            }
                        )
                    }
                }
                userList.find { it.id == selectedUserId }?.let { user ->
                    ProfileDetailModal(
                        user = user,
                        onDismiss = {
                            selectedUserId = null
                        },
                        navigationMeetDetail = {
                            selectedUserId = null
                            navigationMeetDetail(ScreenRoute.Home.FriendMeetDetail(detailUserId = user.id))
                        },
                        updateFavorite = updateFavorite
                    )
                }
            } else {
                //검색 값이 있는 경우
                val filterNameList = userList.filter { it.name.contains(searchValue) }
                if (filterNameList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
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
                        ) { user ->
                            UserItemViewByListView(
                                user = user,
                                viewType = viewType,
                                deleteUser = deleteUser,
                                updateFavorite = updateFavorite,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun UserListHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(modifier),
        text = title,
        fontSize = 16.sp,
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun UserItemViewByListView(
    user: User,
    viewType: FriendListViewType,
    deleteUser: (id: Long) -> Unit,
    updateFavorite: (id: Long, favorite: Boolean) -> Unit,
    userClickAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showDeleteUser by remember {
        mutableStateOf(false)
    }
    UserItemView(
        user = user,
        type = if (viewType == FriendListViewType.Default) {
            UserViewType.Favorite(user.isFavorite)
        } else {
            UserViewType.Delete
        },
        iconClickAction = {
            if (viewType == FriendListViewType.Default) {
                //즐겨 찾기 업데이트
                updateFavorite(user.id, !user.isFavorite)
            } else {
                // 삭제 창
                showDeleteUser = true
            }
        },
        userClickAction = userClickAction,
        modifier = modifier
    )
    if (showDeleteUser) {
        DeleteUserModal(
            onDismiss = {
                showDeleteUser = false
            },
            onDelete = {
                deleteUser(user.id)
            }
        )
    }
}

internal class UserPreviewParameterProvider() : PreviewParameterProvider<List<User>> {
    override val values: Sequence<List<User>>
        get() = sequenceOf(
            listOf(
                User(1, "C_tester1"),
                User(2, "A_tester2"),
                User(3, "B_tester3"),
            ).sortedBy { it.name },
            listOf(
                User(1, "C_tester1"),
                User(2, "A_tester2"),
                User(3, "B_tester3", "", true),
                User(4, "E_tester4", "", false),
                User(5, "D_tester5", "", true),
            ).sortedBy { it.name },
            emptyList()
        )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun UserListViewPreview(
    @PreviewParameter(UserPreviewParameterProvider::class) data: List<User>
) {
    FriedListContent(
        userList = data,
        navigationMeetDetail = {},
        deleteUser = {},
        updateFavorite = { _, _ -> },
        modifier = Modifier
            .safeDrawingPadding()
            .padding(12.dp)
    )
}