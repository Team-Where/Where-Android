package com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.PLACE_STATE_PICK
import com.sooum.domain.model.PlaceItem
import com.sooum.where_android.databinding.ItemSelectedPlaceBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.PlaceBaseAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.PlaceClickCallBack
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.placeItemDiffUtil
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.viewholder.initDefaultPlace

class SelectedPlaceListAdapter() :
    PlaceBaseAdapter<PlaceItem, SelectedPlaceListAdapter.MyView>(placeItemDiffUtil) {

    constructor(callBack: PlaceClickCallBack) : this() {
        setCallBack(callBack)
    }

    inner class MyView(private val binding: ItemSelectedPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(placeItem: PlaceItem) {
            val place = placeItem.place
            with(binding) {
                contentArea.initDefaultPlace(placeItem, placeClickCallBack)
                if (place.status == PLACE_STATE_PICK) {
                    pickContent.visibility = View.VISIBLE
                } else {
                    pickContent.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {
        val view =
            ItemSelectedPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(currentList[position])
    }
}