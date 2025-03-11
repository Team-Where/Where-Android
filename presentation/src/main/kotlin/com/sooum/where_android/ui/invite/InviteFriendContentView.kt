package com.sooum.where_android.ui.invite

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sooum.domain.model.User
import com.sooum.where_android.R
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.UserItemView
import com.sooum.where_android.view.widget.UserViewType

@Composable
fun InviteFriendContentView(
    recentUserList: List<User>,
    userList: List<User>,
    inviteFriend: (User) -> Unit,
    headerColor: Color = Color(0xff374151)
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
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.image_kakao_icon,
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified

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
                    fontSize = 14.sp,
                    color = headerColor
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
                    fontSize = 14.sp,
                    color = headerColor
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
private fun InviteFriendViewPreview() {
    InviteFriendContentView(
        recentUserList =
            listOf(
                User(1, "냠냠")
            ),
        userList = listOf(
            User(2, "냠냠")
        ),
        inviteFriend = {}
    )
}