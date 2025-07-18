package com.sooum.where_android.view.main.myMeetDetail.modal.invite

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
import com.sooum.domain.model.Friend
import com.sooum.domain.model.toUser
import com.sooum.where_android.R
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.widget.UserItemView
import com.sooum.where_android.view.widget.UserViewType
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

@Composable
fun InviteFriendContentView(
    friendList: List<Friend>,
    invitedFriedIdSet: Set<Int> = emptySet(),
    inviteFriend: (Friend) -> Unit,
    headerColor: Color = Color(0xff374151),
    kakaoClickAction: (() -> Unit)? = null,
) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val oneMonthAgo = today.minus(value = 1, unit = DateTimeUnit.MONTH)

    val recentFriendList = friendList.filter { friend ->
        //한달내 모임이 있는 경우를 최근만난 친구로 본다.
        friend.meetList.any { meet ->
            val meetDate = LocalDate.parse(meet.date.date)
            meetDate >= oneMonthAgo
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 20.dp,
                bottom = 20.dp
            )
    ) {
        kakaoClickAction?.let { action ->
            Button(
                onClick = action,
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
                items = recentFriendList,
                key = {
                    "rc" + it.id
                }
            ) { friend ->
                UserItemView(
                    user = friend.toUser(),
                    type = UserViewType.Invite(
                        finish = invitedFriedIdSet.contains(friend.id),
                        meetCount = friend.meetList.size
                    ),
                    iconClickAction = {
                        inviteFriend(friend)
                    }
                )
            }
            item {
                Text(
                    text = "친구(${friendList.size})",
                    modifier = Modifier.height(20.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard,
                    fontSize = 14.sp,
                    color = headerColor
                )
            }
            items(
                items = friendList,
                key = {
                    "d" + it.id
                }
            ) { friend ->
                UserItemView(
                    user = friend.toUser(),
                    type = UserViewType.Invite(
                        finish = invitedFriedIdSet.contains(friend.id),
                        meetCount = friend.meetList.size
                    ),
                    iconClickAction = {
                        inviteFriend(friend)
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
        friendList = listOf(
            Friend(2, "냠냠")
        ),
        inviteFriend = {}
    )
}