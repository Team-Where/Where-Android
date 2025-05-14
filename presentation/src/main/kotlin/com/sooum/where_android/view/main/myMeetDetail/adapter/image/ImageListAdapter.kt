package com.sooum.where_android.view.main.myMeetDetail.adapter.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sooum.domain.model.ProfileImage
import com.sooum.where_android.databinding.ItemProfileImageBinding
import com.sooum.where_android.databinding.ItemProfileImageOverflowBinding
import com.sooum.where_android.util.ProfileDiffUtil

class ImageListAdapter : ListAdapter<ProfileImage, RecyclerView.ViewHolder>(ProfileDiffUtil) {

    companion object {
        private const val MAX_DISPLAY_COUNT = 5
        private const val TYPE_NORMAL = 0
        private const val TYPE_OVERFLOW = 1
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() > MAX_DISPLAY_COUNT) MAX_DISPLAY_COUNT else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == MAX_DISPLAY_COUNT - 1 && super.getItemCount() > MAX_DISPLAY_COUNT) {
            TYPE_OVERFLOW
        } else {
            TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_OVERFLOW) {
            val binding = ItemProfileImageOverflowBinding.inflate(inflater, parent, false)
            OverflowViewHolder(binding)
        } else {
            val binding = ItemProfileImageBinding.inflate(inflater, parent, false)
            NormalViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is NormalViewHolder) {
            holder.bind(item)
        } else if (holder is OverflowViewHolder) {
            val extraCount = super.getItemCount() - MAX_DISPLAY_COUNT
            holder.bind(item, extraCount)
        }
    }

    class NormalViewHolder(private val binding: ItemProfileImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileImage) {
            Glide.with(binding.root).load(item.profileImage).circleCrop().into(binding.profileImage)
        }
    }

    class OverflowViewHolder(private val binding: ItemProfileImageOverflowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileImage, overflowCount: Int) {
            Glide.with(binding.root).load(item.profileImage).circleCrop().into(binding.imageProfile)
            binding.textOverflow.text = "+$overflowCount"
            binding.textOverflow.visibility = ViewGroup.VISIBLE
        }
    }
}
