package com.sooum.where_android.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.GrayScale600
import com.sooum.where_android.theme.GrayScale700
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
            val favoriteUserList = userList.filter { it.isFavorite }

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
                    items = userList,
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

@Composable
@Preview(showBackground = true)
fun UserListViewPreview() {
    val data = listOf(
        User(1, "C_tester1"),
        User(2, "A_tester2"),
        User(3, "B_tester3", "", true),
    )
    UserListView(
        emptyList(),
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    )
}