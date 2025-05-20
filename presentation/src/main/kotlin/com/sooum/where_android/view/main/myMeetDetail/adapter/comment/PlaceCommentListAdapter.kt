package com.sooum.where_android.view.main.myMeetDetail.adapter.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.CommentListItem
import com.sooum.where_android.databinding.ItemPlaceCommentFalseBinding
import com.sooum.where_android.databinding.ItemPlaceCommentTrueBinding
import com.sooum.where_android.util.CommentDiffUtil

class PlaceCommentListAdapter(
    private val onCommentClick: (CommentListItem) -> Unit
) : ListAdapter<CommentListItem, RecyclerView.ViewHolder>(CommentDiffUtil) {


    companion object {
        private const val VIEW_TYPE_MINE = 1
        private const val VIEW_TYPE_OTHER = 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isMine) VIEW_TYPE_MINE else VIEW_TYPE_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_MINE) {
            val binding = ItemPlaceCommentTrueBinding.inflate(inflater, parent, false)
            MineViewHolder(binding, onCommentClick)
        } else {
            val binding = ItemPlaceCommentFalseBinding.inflate(inflater, parent, false)
            OtherViewHolder(binding, onCommentClick)
        }
    }

    fun submitListWithMineFirst(list: List<CommentListItem>) {
        val sortedList = list.sortedWith(compareByDescending { it.isMine })
        submitList(sortedList)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MineViewHolder -> holder.bind(item)
            is OtherViewHolder -> holder.bind(item)
        }
    }

    class MineViewHolder(
        private val binding: ItemPlaceCommentTrueBinding,
        private val onClick: (CommentListItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommentListItem) {
            binding.textDescription.text = item.description
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    class OtherViewHolder(
        private val binding: ItemPlaceCommentFalseBinding,
        private val onClick: (CommentListItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommentListItem) {
            binding.textDescription.text = item.description
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}

