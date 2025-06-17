package com.sooum.where_android.util

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.InquiryResult
import com.sooum.domain.model.NoticeResult

object InquiryDiffUtil : DiffUtil.ItemCallback<InquiryResult>() {
    override fun areItemsTheSame(oldItem: InquiryResult, newItem: InquiryResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: InquiryResult, newItem: InquiryResult): Boolean {
        return oldItem == newItem
    }
}