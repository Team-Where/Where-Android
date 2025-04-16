package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.sooum.domain.model.PlaceRank
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ItemProfilePlaceListBinding
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding

class BestPlaceListAdapter : PlaceBaseAdapter<PlaceRank, RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1

        val diffUtil = object : DiffUtil.ItemCallback<PlaceRank>() {
            override fun areItemsTheSame(oldItem: PlaceRank, newItem: PlaceRank): Boolean {
                return if (oldItem is PlaceRank.RankHeader && newItem is PlaceRank.RankHeader) {
                    oldItem.rank == newItem.rank
                } else if (oldItem is PlaceRank.PostItem && newItem is PlaceRank.PostItem) {
                    (oldItem.rank == newItem.rank) && (oldItem.place.id == newItem.place.id)
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: PlaceRank, newItem: PlaceRank): Boolean {
                return if (oldItem is PlaceRank.RankHeader && newItem is PlaceRank.RankHeader) {
                    oldItem == newItem
                } else if (oldItem is PlaceRank.PostItem && newItem is PlaceRank.PostItem) {
                    (oldItem.rank == newItem.rank) && (oldItem.place == newItem.place)
                } else {
                    false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is PlaceRank.RankHeader -> VIEW_TYPE_HEADER
            is PlaceRank.PostItem -> VIEW_TYPE_ITEM
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
        when (val item = currentList[position]) {
            is PlaceRank.RankHeader -> (holder as ProfileHeaderViewHolder).bind(item)
            is PlaceRank.PostItem -> (holder as PostViewHolder).bind(item)
        }
    }

    inner class ProfileHeaderViewHolder(private val binding: ItemProfilePlaceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceRank.RankHeader) {
            binding.textName.text = item.rank.toString()
        }
    }

    inner class PostViewHolder(private val binding: ItemUnselectedPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceRank.PostItem) {
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