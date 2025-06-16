package com.sooum.where_android.view.hamburger.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.FaqItem
import com.sooum.domain.model.NoticeResult
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ItemFaqBinding
import com.sooum.where_android.databinding.ItemNoticeBinding
import com.sooum.where_android.util.NoticeDiffUtil

class FaqRecyclerView (
    private val noticeList: List<FaqItem>
) : RecyclerView.Adapter<FaqRecyclerView.FaqViewHolder>() {

    private val expandedPositions = mutableSetOf<Int>()

    inner class FaqViewHolder(private val binding: ItemFaqBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) = with(binding) {
            val item = noticeList[position]
            textTitle.text = item.title
            textContent.text = item.content

            val isExpanded = expandedPositions.contains(position)
            expandLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            imageExpand.setImageResource(
                if (isExpanded) R.drawable.icon_arrow_up else R.drawable.icon_arrow_down
            )

            root.setOnClickListener {
                if (expandedPositions.contains(position)) {
                    expandedPositions.remove(position)
                } else {
                    expandedPositions.add(position)
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqRecyclerView.FaqViewHolder {
        val binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaqRecyclerView.FaqViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = noticeList.size
}
