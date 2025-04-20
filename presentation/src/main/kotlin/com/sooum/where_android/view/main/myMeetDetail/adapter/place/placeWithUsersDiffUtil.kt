package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.PlaceWithUsers

internal val placeWithUsersDiffUtil = object : DiffUtil.ItemCallback<PlaceWithUsers>() {
    override fun areItemsTheSame(oldItem: PlaceWithUsers, newItem: PlaceWithUsers): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: PlaceWithUsers, newItem: PlaceWithUsers): Boolean {
        return oldItem == newItem
    }
}