package com.sooum.where_android.view.main.myMeetDetail.adapter.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.PlaceList
import com.sooum.where_android.databinding.ItemProfilePlaceListBinding
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.viewholder.PostViewHolder

class AllPlaceListAdapter() : PlaceBaseAdapter<PlaceList, RecyclerView.ViewHolder>(diffUtil) {
    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1

        private val diffUtil = object : DiffUtil.ItemCallback<PlaceList>() {
            override fun areItemsTheSame(oldItem: PlaceList, newItem: PlaceList): Boolean {
                return if (oldItem is PlaceList.ProfileHeader && newItem is PlaceList.ProfileHeader) {
                    oldItem.userId == newItem.userId
                } else if (oldItem is PlaceList.PostItem && newItem is PlaceList.PostItem) {
                    (oldItem.userId == newItem.userId) && (oldItem.place.id == newItem.place.id)
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: PlaceList, newItem: PlaceList): Boolean {
                return if (oldItem is PlaceList.ProfileHeader && newItem is PlaceList.ProfileHeader) {
                    oldItem == newItem
                } else if (oldItem is PlaceList.PostItem && newItem is PlaceList.PostItem) {
                    (oldItem.userId == newItem.userId) && (oldItem.place == newItem.place)
                } else {
                    false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
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
                PostViewHolder(binding, placeClickCallBack)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = currentList[position]) {
            is PlaceList.ProfileHeader -> (holder as ProfileHeaderViewHolder).bind(item)
            is PlaceList.PostItem -> (holder as PostViewHolder).bind(item.place)
        }
    }

    inner class ProfileHeaderViewHolder(private val binding: ItemProfilePlaceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceList.ProfileHeader) {
            binding.textName.text = item.userName
        }
    }
}
