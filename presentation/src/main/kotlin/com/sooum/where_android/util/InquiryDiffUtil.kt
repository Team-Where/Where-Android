package com.sooum.where_android.util

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.InquiryWriteResult

object InquiryDiffUtil : DiffUtil.ItemCallback<InquiryWriteResult>() {
    override fun areItemsTheSame(oldItem: InquiryWriteResult, newItem: InquiryWriteResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: InquiryWriteResult, newItem: InquiryWriteResult): Boolean {
        return oldItem == newItem
    }
}