package com.sooum.where_android.view.main.myMeetDetail.adapter.firend

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.InvitedFriend
import com.sooum.where_android.view.common.ComposeItemAdapter

/**
 * InvitedFriend 리스트를 출력하는 FriendAdapter
 */
abstract class FriendBaseAdapter() : ComposeItemAdapter<InvitedFriend>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<InvitedFriend>() {
            override fun areItemsTheSame(oldItem: InvitedFriend, newItem: InvitedFriend): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: InvitedFriend,
                newItem: InvitedFriend
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}