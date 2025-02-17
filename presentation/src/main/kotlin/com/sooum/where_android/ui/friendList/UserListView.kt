package com.sooum.where_android.ui.friendList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.Gray100
import com.sooum.where_android.theme.Gray400
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.Gray700
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.Red500
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.widget.UserItemView
import com.sooum.where_android.widget.UserViewType


sealed class UserListViewType(
    val title: String
) {
    data object Default : UserListViewType("편집")
    data object Edit : UserListViewType("완료")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListView(
    userList: List<User>,
    modifier: Modifier = Modifier
) {
    var viewType: UserListViewType by remember {
        mutableStateOf(UserListViewType.Default)
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
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "친구목록",
                color = Gray800,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = pretendard
            )

            TextButton(
                onClick = {
                    viewType = when (viewType) {
                        UserListViewType.Default -> {
                            UserListViewType.Edit
                        }

                        UserListViewType.Edit -> {
                            UserListViewType.Default
                        }
                    }
                },
                enabled = userList.isNotEmpty()
            ) {
                Text(
                    text = viewType.title,
                    color = if (userList.isEmpty()) Color.Gray else Primary600,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
            }
        }

        TextField(
            value = searchValue,
            onValueChange = {
                searchValue = it
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Filled.Search, null)
            },
            trailingIcon = if (searchValue.isEmpty()) {
                null
            } else {
                {
                    IconButton(
                        onClick = {
                            searchValue = ""
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_clear),
                            contentDescription = "search item clear",
                            tint = Gray400
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    text = "친구를 검색하세요.",
                    fontFamily = pretendard,
                    fontSize = 16.sp,
                    color = Gray500
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Gray100,
                unfocusedContainerColor = Gray100,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )
        Spacer(
            Modifier.height(
                height = 31.dp
            )
        )
        if (userList.isEmpty()) {
            //아에 정보가 없는 경우
            Column {
                UserListHeader("친구(${userList.size})")
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
                        text = "아직 만난 친구가 없어요!",
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

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (viewType == UserListViewType.Default) {
                        //즐겨 찾기는 기본 모드에서만(검색값이 없을때만)
                        item {
                            UserListHeader("즐겨찾기(${favoriteUserList.size})")
                        }
                        items(
                            items = favoriteUserList,
                            key = {
                                "fv" + it.id
                            }
                        ) { user ->
                            UserItemView(
                                user = user,
                                type = UserViewType.Favorite(user.isFavorite)
                            )
                        }
                    }

                    item {
                        UserListHeader("친구(${userList.size})")
                    }
                    items(
                        items = userList,
                        key = {
                            "df" + it.id
                        }
                    ) { user ->
                        UserItemViewByListView(
                            user = user,
                            viewType = viewType
                        )
                    }
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
                            text = "검색 결과가 없습니다.",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Gray700
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = filterNameList,
                            key = {
                                "df_f" + it.id
                            }
                        ) { user ->
                            UserItemViewByListView(
                                user = user,
                                viewType = viewType
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
    title: String
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        fontSize = 16.sp,
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserItemViewByListView(
    user: User,
    viewType: UserListViewType,
) {
    var showDeleteUser by remember {
        mutableStateOf(false)
    }
    UserItemView(
        user = user,
        type = if (viewType == UserListViewType.Default) {
            UserViewType.Favorite(user.isFavorite)
        } else {
            UserViewType.Delete
        },
        iconClickAction = {
            if (viewType == UserListViewType.Default) {
                //즐겨 찾기 업데이트
            } else {
                // 삭제 창
                showDeleteUser = true
            }
        }
    )
    if (showDeleteUser) {
        ModalBottomSheet(
            onDismissRequest = {
                showDeleteUser = false
            },
            dragHandle = null,
            containerColor = Color.White,
        ) {
            Column(
                modifier = Modifier.height(150.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        showDeleteUser = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray100
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .height(54.dp)
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "친구 삭제",
                        color = Red500,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard,
                        fontSize = 16.sp
                    )
                }
            }
        }
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
@Preview(showBackground = true)
fun UserListViewPreview(
    @PreviewParameter(UserPreviewParameterProvider::class) data: List<User>
) {
    UserListView(
        data,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    )
}