package com.wodud7308.movieinfo.core.ui.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<T : Any, VH : BaseViewHolder<T, *>>(
    diff: DiffUtil.ItemCallback<T>,
    var itemClickListener: ItemClickListener<T>? = null
) : ListAdapter<T, VH>(diff)
