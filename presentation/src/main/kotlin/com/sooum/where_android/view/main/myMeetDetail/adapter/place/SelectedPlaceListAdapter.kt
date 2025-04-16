package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.sooum.domain.model.Place
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ItemSelectedPlaceBinding

class SelectedPlaceListAdapter() :
    PlaceBaseAdapter<Place, SelectedPlaceListAdapter.MyView>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MyView(private val binding: ItemSelectedPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            with(binding) {
                val context = root.context
                updateLikeUI(context, place.myLike, place.likeCount)
                with(contentArea) {
                    textPlaceName.text = place.name
                    textAddress.text = place.address
                    textCommentNumber.text = "0"
                    textLikeNumber.text = place.likeCount.toString()

                    btnNaverMap.setOnClickListener {
                        placeClickCallBack?.startNaverMapUri(place.naverLink)
                    }
                    btnKakaoMap.setOnClickListener {
                        placeClickCallBack?.startKakaoMapUri(place.kakaoLink)
                    }
                }

                if (place.status == "Picked") {
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
        holder.bind(currentList[position])
    }
}