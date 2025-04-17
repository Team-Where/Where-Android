package com.sooum.where_android.view.common

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil

/**
 * ViewHolder가 [ComposeItemViewHolder]로 고정된 [BaseAdapter]형태
 */
abstract class ComposeItemAdapter<T>(
    diffUtil: DiffUtil.ItemCallback<T>
) : BaseAdapter<T, ComposeItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComposeItemViewHolder {
        return ComposeItemViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ComposeItemViewHolder, position: Int) {
        holder.composeView.setContent {
            Bind(currentList[position])
        }
    }

    @Composable
    abstract fun Bind(item: T)

    override fun onViewRecycled(holder: ComposeItemViewHolder) {
        super.onViewRecycled(holder)
        holder.composeView.disposeComposition()
    }

}