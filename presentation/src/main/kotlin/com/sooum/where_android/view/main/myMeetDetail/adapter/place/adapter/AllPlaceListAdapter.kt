package com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sooum.domain.model.PlaceItem
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.PlaceBaseAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.placeItemDiffUtil
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.viewholder.PostViewHolder

class AllPlaceListAdapter() : PlaceBaseAdapter<PlaceItem, PostViewHolder>(
    placeItemDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUnselectedPlaceBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding, placeClickCallBack)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
