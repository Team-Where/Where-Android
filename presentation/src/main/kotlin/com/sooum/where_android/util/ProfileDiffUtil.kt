package com.sooum.where_android.util

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.ProfileImage

object ProfileDiffUtil : DiffUtil.ItemCallback<ProfileImage>() {
    override fun areItemsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
        return oldItem.profileImage == newItem.profileImage
    }

    override fun areContentsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
        return oldItem == newItem
    }
}