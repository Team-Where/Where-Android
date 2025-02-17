package com.sooum.where_android.ui.main

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
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
import com.sooum.where_android.theme.Gray500
import com.sooum.where_android.theme.Gray800
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.widget.UserItemView
import com.sooum.where_android.widget.UserViewType

@Composable
fun UserListView(
    userList: List<User>,
    modifier: Modifier = Modifier
) {
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
                onClick = {}
            ) {
                Text(
                    text = "편집",
                    color = Primary600,
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
                Icon(Icons.Filled.Search,null)
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
                height = 21.dp
            )
        )
        if (userList.isEmpty()) {
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
            val filterNameList = userList.filter { it.name.contains(searchValue) }
            val favoriteUserList = filterNameList.filter { it.isFavorite }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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
                        user,
                        UserViewType.Favorite(user.isFavorite)
                    )
                }
                item {
                    UserListHeader("친구(${userList.size})")
                }
                items(
                    items = filterNameList,
                    key = {
                        "df" + it.id
                    }
                ) { user ->
                    UserItemView(
                        user,
                        UserViewType.Favorite(user.isFavorite)
                    )
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