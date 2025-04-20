package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import androidx.recyclerview.widget.DiffUtil
import com.sooum.domain.model.PlaceItem

internal val placeItemDiffUtil = object : DiffUtil.ItemCallback<PlaceItem>() {
    override fun areItemsTheSame(oldItem: PlaceItem, newItem: PlaceItem): Boolean {
        return (oldItem.placeId == newItem.placeId)
    }

    override fun areContentsTheSame(oldItem: PlaceItem, newItem: PlaceItem): Boolean {
        return (oldItem == newItem)
    }
}