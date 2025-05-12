package com.sooum.where_android.util

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.CommentListItem

object CommentDiffUtil : DiffUtil.ItemCallback<CommentListItem>() {
    override fun areItemsTheSame(oldItem: CommentListItem, newItem: CommentListItem): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: CommentListItem, newItem: CommentListItem): Boolean {
        return oldItem == newItem
    }

}