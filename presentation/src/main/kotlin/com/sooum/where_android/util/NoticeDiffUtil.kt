package com.sooum.where_android.util

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.NoticeResult
import com.sooum.domain.model.ProfileImage

object NoticeDiffUtil : DiffUtil.ItemCallback<NoticeResult>() {
    override fun areItemsTheSame(oldItem: NoticeResult, newItem: NoticeResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoticeResult, newItem: NoticeResult): Boolean {
        return oldItem == newItem
    }
}