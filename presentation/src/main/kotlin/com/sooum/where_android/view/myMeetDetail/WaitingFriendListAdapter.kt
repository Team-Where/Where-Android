package com.sooum.where_android.view.myMeetDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.InvitedFriend
import com.sooum.where_android.databinding.ItemInvitedFriendListBinding
import com.sooum.where_android.databinding.ItemWaitingFriendListBinding

class WaitingFriendListAdapter() : RecyclerView.Adapter<WaitingFriendListAdapter.MyView>() {
    private var waitingFriendList:  List<InvitedFriend> = emptyList()

    inner class MyView(private val binding: ItemWaitingFriendListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(waitingFriendModel: InvitedFriend) {
            binding.textName.text = waitingFriendModel.name

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaitingFriendListAdapter.MyView {
        val view = ItemWaitingFriendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: WaitingFriendListAdapter.MyView, position: Int) {
        holder.bind(waitingFriendList[position])
    }

    override fun getItemCount(): Int {
        return waitingFriendList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<InvitedFriend>) {
        waitingFriendList = list
        notifyDataSetChanged()
    }
}