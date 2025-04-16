package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.sooum.domain.model.SelectedPlace
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ItemSelectedPlaceBinding

class SelectedPlaceListAdapter() :
    PlaceBaseAdapter<SelectedPlace, SelectedPlaceListAdapter.MyView>() {

    inner class MyView(private val binding: ItemSelectedPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(selectedPlaceModel: SelectedPlace) {
            with(binding) {
                val context = root.context
                updateLikeUI(context, selectedPlaceModel.myLike, selectedPlaceModel.likeCount)
                with(contentArea) {
                    textPlaceName.text = selectedPlaceModel.name
                    textAddress.text = selectedPlaceModel.address
                    textCommentNumber.text = selectedPlaceModel.commentCount.toString()
                    textLikeNumber.text = selectedPlaceModel.likeCount.toString()

                    btnNaverMap.setOnClickListener {
                        placeClickCallBack?.startNaverMapUri(selectedPlaceModel.naverLink)
                    }
                    btnKakaoMap.setOnClickListener {
                        placeClickCallBack?.startKakaoMapUri(selectedPlaceModel.kakaoLink)
                    }
                }

                if (selectedPlaceModel.status == "Picked") {
                    pickContent.visibility = View.VISIBLE
                } else {
                    pickContent.visibility = View.INVISIBLE
                }
            }
        }

        private fun updateLikeUI(context: Context, isLiked: Boolean, likeCount: Int) {
            val color = if (isLiked) {
                ContextCompat.getColor(context, R.color.main_color)
            } else {
                ContextCompat.getColor(context, R.color.gray_600)
            }

            with(binding.contentArea) {
                textLikeNumber.setTextColor(color)
                textLike.setTextColor(color)
                iconHeart.load(if (isLiked) R.drawable.icon_heart_fill else R.drawable.icon_heart)
                textLikeNumber.text = if (likeCount > 0) likeCount.toString() else ""
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
        holder.bind(items[position])
    }
}