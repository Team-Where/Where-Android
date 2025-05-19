package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OverlapDecoration(private val overlapWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: android.graphics.Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.set(-overlapWidth, 0, 0, 0)
        }
    }
}