package com.sooum.where_android.view.main.myMeetDetail.adapter.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.sooum.domain.model.ProfileImage
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ItemProfileImageOverflowBinding
import com.sooum.where_android.util.ProfileDiffUtil
import com.sooum.where_android.view.main.myMeetDetail.adapter.image.ImageListAdapter.OverflowViewHolder

class ImageListAdapter : ListAdapter<ProfileImage, OverflowViewHolder>(ProfileDiffUtil) {

    companion object {
        private const val MAX_DISPLAY_COUNT = 5
        private const val TYPE_NORMAL = 0
        private const val TYPE_OVERFLOW = 1
    }

    override fun getItemCount(): Int {
        return (super.getItemCount()).takeIf { it <= MAX_DISPLAY_COUNT } ?: MAX_DISPLAY_COUNT
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == MAX_DISPLAY_COUNT - 1 && super.getItemCount() > MAX_DISPLAY_COUNT) {
            TYPE_OVERFLOW
        } else {
            TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverflowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileImageOverflowBinding.inflate(inflater, parent, false)
        return OverflowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OverflowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        if (getItemViewType(position) == TYPE_OVERFLOW) {
            val extraCount = super.getItemCount() - MAX_DISPLAY_COUNT
            holder.bindCount(extraCount)
        }
    }

    class OverflowViewHolder(
        private val binding: ItemProfileImageOverflowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileImage) {
            with(binding) {
                imageProfile.load(
                    data = item.profileImage
                ) {
                    placeholder(R.drawable.image_profile_default_cover)
                    error(R.drawable.image_profile_default_cover)
                }
                textOverflow.visibility = View.GONE
            }
        }

        fun bindCount(overflowCount: Int) {
            with(binding) {
                with(textOverflow) {
                    text = "+$overflowCount"
                    visibility = if (overflowCount > 0) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }
}
