package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sooum.where_android.view.common.BaseAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.PlaceClickCallBack

abstract class PlaceBaseAdapter<ITEM, HOLDER : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : BaseAdapter<ITEM, HOLDER>(diffUtil) {

    protected var placeClickCallBack: PlaceClickCallBack? = null

    fun setCallBack(callBack: PlaceClickCallBack) {
        placeClickCallBack = callBack
    }
}