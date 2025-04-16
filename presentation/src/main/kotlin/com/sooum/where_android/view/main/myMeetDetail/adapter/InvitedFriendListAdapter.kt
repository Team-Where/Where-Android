package com.sooum.where_android.view.main.myMeetDetail.adapter

import androidx.compose.runtime.Composable
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.User
import com.sooum.where_android.view.common.ComposeItemAdapter
import com.sooum.where_android.view.widget.UserItemView
import com.sooum.where_android.view.widget.UserViewType

class InvitedFriendListAdapter() : ComposeItemAdapter<InvitedFriend>() {

    interface OnItemClickEventListener {
        fun clickedUserIcon(item: InvitedFriend)
    }

    private var itemClickListener: OnItemClickEventListener? = null
    fun setItemClickListener(listener: OnItemClickEventListener) {
        itemClickListener = listener
    }

    @Composable
    override fun Bind(item: InvitedFriend) {
        UserItemView(
            user = User(
                id = item.id,
                name = item.name,
                profileImage = item.image ?: ""
            ),
            type = if (item.isMe) UserViewType.Nothing else UserViewType.Option,
            iconClickAction = {
                itemClickListener?.clickedUserIcon(item)
            }
        )
    }

}