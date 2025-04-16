package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import androidx.recyclerview.widget.RecyclerView

abstract class PlaceBaseAdapter<ITEM, HOLDER : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<HOLDER>() {

    protected var items: List<ITEM> = emptyList()

    fun setData(items: List<ITEM>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    protected var placeClickCallBack: PlaceClickCallBack? = null

    fun setCallBack(callBack: PlaceClickCallBack) {
        placeClickCallBack = callBack
    }

    override fun getItemCount(): Int {
        return items.size
    }
}