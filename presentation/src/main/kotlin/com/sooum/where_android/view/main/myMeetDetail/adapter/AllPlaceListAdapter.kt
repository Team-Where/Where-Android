package com.sooum.where_android.view.main.myMeetDetail.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.PlaceList
import com.sooum.where_android.databinding.ItemProfilePlaceListBinding
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding
import androidx.core.net.toUri

class AllPlaceListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<PlaceList> = emptyList()

    fun setData(items: List<PlaceList>) {
        this.items = items
        this.notifyDataSetChanged()
    }

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
            val place = item.place
            with(binding) {
                textPlaceName.text = place.name
                textAddress.text = place.address
                if (place.likeCount > 0) {
                    textLikeNumber.text = place.likeCount.toString()
                }

                btnNaverMap.setOnClickListener {
                    makeIntent(it.context, place.naverLink)
                }

                btnKakaoMap.setOnClickListener {
                    makeIntent(it.context, place.kakaoLink)
                }
                if(place.together) {
                    textTogether.visibility = View.VISIBLE
                } else {
                    textTogether.visibility = View.GONE
                }
            }
        }
    }

    private fun makeIntent(
        context: Context,
        uriString: String
    ) {
        val intent = Intent(Intent.ACTION_VIEW, uriString.toUri())
        context.startActivity(intent)
    }
}
