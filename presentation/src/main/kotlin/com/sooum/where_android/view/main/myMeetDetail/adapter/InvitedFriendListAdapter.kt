package com.sooum.where_android.view.main.myMeetDetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.InvitedFriend
import com.sooum.where_android.databinding.ItemInvitedFriendListBinding

class InvitedFriendListAdapter() : RecyclerView.Adapter<InvitedFriendListAdapter.MyView>() {
    private var invitedFriendList:  List<InvitedFriend> = emptyList()

    inner class MyView(private val binding: ItemInvitedFriendListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(invitedFriendModel: InvitedFriend) {
            binding.textName.text = invitedFriendModel.name

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {
        val view = ItemInvitedFriendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(invitedFriendList[position])
    }

    override fun getItemCount(): Int {
        return invitedFriendList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<InvitedFriend>) {
        invitedFriendList = list
        notifyDataSetChanged()
    }
}