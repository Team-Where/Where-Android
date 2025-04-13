package com.sooum.where_android.view.common

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView

class ComposeItemViewHolder(
    val composeView: ComposeView
) : RecyclerView.ViewHolder(composeView) {
    init {
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
    }
}

abstract class ComposeItemAdapter<T> : RecyclerView.Adapter<ComposeItemViewHolder>() {
    protected var itemList: List<T> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComposeItemViewHolder {
        return ComposeItemViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ComposeItemViewHolder, position: Int) {
        holder.composeView.setContent {
            Bind(itemList[position])
        }
    }

    @Composable
    abstract fun Bind(item:T)

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onViewRecycled(holder: ComposeItemViewHolder) {
        super.onViewRecycled(holder)
        holder.composeView.disposeComposition()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<T>) {
        itemList = list
        notifyDataSetChanged()
    }

}