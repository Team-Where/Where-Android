package com.sooum.where_android.view.common

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView

/**
 * composeView와 매핑된 ViewHolder
 */
open class ComposeItemViewHolder(
    val composeView: ComposeView
) : RecyclerView.ViewHolder(composeView) {
    init {
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
    }
}

