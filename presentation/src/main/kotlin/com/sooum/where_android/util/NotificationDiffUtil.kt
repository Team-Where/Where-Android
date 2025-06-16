package com.sooum.where_android.util

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.NoticeResult
import com.sooum.domain.model.NotificationItem

object NotificationDiffUtil : DiffUtil.ItemCallback<NotificationItem>() {
    override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem == newItem
    }
}