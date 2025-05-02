package com.sooum.where_android.view.main.myMeetDetail.adapter.firend

import androidx.compose.runtime.Composable
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.User
import com.sooum.where_android.view.widget.UserItemView
import com.sooum.where_android.view.widget.UserViewType

/**
 * 초대된 친구 목록 리스트 어댑터
 */
class InvitedFriendListAdapter() : FriendBaseAdapter() {

    @Composable
    override fun Bind(item: InvitedFriend) {
        UserItemView(
            user = User(
                id = item.id,
                name = item.name,
                profileImage = item.image ?: ""
            ),
            type = UserViewType.Nothing
        )
    }
}