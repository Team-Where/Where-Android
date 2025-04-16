package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.sooum.domain.model.PlaceList
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ItemProfilePlaceListBinding
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding

class AllPlaceListAdapter() : PlaceBaseAdapter<PlaceList, RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PlaceList.ProfileHeader -> VIEW_TYPE_HEADER
            is PlaceList.PostItem -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemProfilePlaceListBinding.inflate(inflater, parent, false)
                ProfileHeaderViewHolder(binding)
            }

            VIEW_TYPE_ITEM -> {
                val binding = ItemUnselectedPlaceBinding.inflate(inflater, parent, false)
                PostViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is PlaceList.ProfileHeader -> (holder as ProfileHeaderViewHolder).bind(item)
            is PlaceList.PostItem -> (holder as PostViewHolder).bind(item)
        }
    }

    inner class ProfileHeaderViewHolder(private val binding: ItemProfilePlaceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceList.ProfileHeader) {
            binding.textName.text = item.userName
        }
    }

    inner class PostViewHolder(private val binding: ItemUnselectedPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceList.PostItem) {
            val place = item.place
            with(binding) {
                val context = root.context

                updateLikeUI(context, place.myLike, place.likeCount)
                with(contentArea) {
                    textPlaceName.text = place.name
                    textAddress.text = place.address

                    heartArea.setOnClickListener {
                        placeClickCallBack?.likeChange(place.id)
                    }

                    btnNaverMap.setOnClickListener {
                        placeClickCallBack?.startNaverMapUri(place.naverLink)
                    }

                    btnKakaoMap.setOnClickListener {
                        placeClickCallBack?.startKakaoMapUri(place.kakaoLink)
                    }
                }

                if (place.together) {
                    textTogether.visibility = View.VISIBLE
                } else {
                    textTogether.visibility = View.GONE
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

}
