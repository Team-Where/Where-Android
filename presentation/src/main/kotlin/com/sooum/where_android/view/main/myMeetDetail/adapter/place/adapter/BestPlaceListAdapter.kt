package com.sooum.where_android.view.main.myMeetDetail.adapter.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.PlaceRank
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding
import com.sooum.where_android.theme.Primary600
import com.sooum.where_android.theme.pretendard
import com.sooum.where_android.view.common.ComposeItemViewHolder
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.PlaceBaseAdapter
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.viewholder.PostViewHolder

class BestPlaceListAdapter : PlaceBaseAdapter<PlaceRank, RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1

        private val diffUtil = object : DiffUtil.ItemCallback<PlaceRank>() {
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
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                RankHeaderViewHolder(ComposeView(parent.context))
            }

            VIEW_TYPE_ITEM -> {
                val binding = ItemUnselectedPlaceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PostViewHolder(binding, placeClickCallBack)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = currentList[position]) {
            is PlaceRank.RankHeader -> (holder as RankHeaderViewHolder).bind(item)
            is PlaceRank.PostItem -> (holder as PostViewHolder).bind(item.place)
        }
    }

    inner class RankHeaderViewHolder(
        composeView: ComposeView
    ) : ComposeItemViewHolder(composeView) {

        fun bind(item: PlaceRank.RankHeader) {
            composeView.setContent {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Best ${item.rank}",
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Primary600
                    )
                    HorizontalDivider(
                        color = Primary600,
                        modifier = Modifier.width(100.dp)
                    )
                }
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is RankHeaderViewHolder) {
            holder.composeView.disposeComposition()
        }
    }
}