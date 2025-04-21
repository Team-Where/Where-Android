package com.sooum.where_android.view.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * 가장 기본형 ListAdapter
 */
abstract class BaseAdapter<ITEM, HOLDER : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : ListAdapter<ITEM, HOLDER>(diffUtil)