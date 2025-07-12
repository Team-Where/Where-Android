package com.sooum.where_android.view.hamburger.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.InquiryWriteResult
import com.sooum.where_android.R
import com.sooum.where_android.databinding.ItemInquiryCompleteAnswerBinding
import com.sooum.where_android.databinding.ItemInquiryWaitingAnswerBinding
import com.sooum.where_android.util.InquiryDiffUtil

class InquiryRecyclerView(
    private val inquiryList: List<InquiryWriteResult>
) : ListAdapter<InquiryWriteResult, RecyclerView.ViewHolder>(InquiryDiffUtil) {

    companion object {
        private const val TYPE_WAITING = 0
        private const val TYPE_COMPLETE = 1
    }

    private val expandedPositions = mutableSetOf<Int>()

    override fun getItemViewType(position: Int): Int {
        return if (inquiryList[position].answered) TYPE_COMPLETE else TYPE_WAITING
    }

    override fun getItemCount(): Int = inquiryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_COMPLETE) {
            val binding = ItemInquiryCompleteAnswerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            CompleteViewHolder(binding)
        } else {
            val binding = ItemInquiryWaitingAnswerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            WaitingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CompleteViewHolder) {
            holder.bind(position)
        } else if (holder is WaitingViewHolder) {
            holder.bind(position)
        }
    }

    inner class CompleteViewHolder(private val binding: ItemInquiryCompleteAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = with(binding) {
            val item = inquiryList[position]
            textTitle.text = item.title
            textDate.text = item.inquiryDate
            textContent.text = item.content
            textAnswer.text = item.answerContent

            val isExpanded = expandedPositions.contains(position)
            expandLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            imageExpand.setImageResource(
                if (isExpanded) R.drawable.icon_arrow_up else R.drawable.icon_arrow_down
            )

            root.setOnClickListener {
                toggleExpand(position)
            }
        }
    }

    inner class WaitingViewHolder(private val binding: ItemInquiryWaitingAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = with(binding) {
            val item = inquiryList[position]
            textTitle.text = item.title
            textDate.text = item.inquiryDate
            textContent.text = item.content

            val isExpanded = expandedPositions.contains(position)
            expandLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            imageExpand.setImageResource(
                if (isExpanded) R.drawable.icon_arrow_up else R.drawable.icon_arrow_down
            )

            root.setOnClickListener {
                toggleExpand(position)
            }
        }
    }

    private fun toggleExpand(position: Int) {
        if (expandedPositions.contains(position)) {
            expandedPositions.remove(position)
        } else {
            expandedPositions.add(position)
        }
        notifyItemChanged(position)
    }
}

