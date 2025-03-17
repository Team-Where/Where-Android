package com.sooum.where_android.view.main.myMeetDetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.InvitedFriend
import com.sooum.domain.model.PlaceList
import com.sooum.where_android.databinding.ItemProfilePlaceListBinding
import com.sooum.where_android.databinding.ItemSelectedPlaceBinding
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding

class AllPlaceListAdapter(private val items: List<PlaceList>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun getItemCount(): Int = items.size

    inner class ProfileHeaderViewHolder(private val binding: ItemProfilePlaceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceList.ProfileHeader) {
            binding.textName.text = item.userName
        }
    }

    inner class PostViewHolder(private val binding: ItemUnselectedPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceList.PostItem) {
            binding.textPlaceName.text = item.title
            binding.textAddress.text = item.location
        }
    }
}
