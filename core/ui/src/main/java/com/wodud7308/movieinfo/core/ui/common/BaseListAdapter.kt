package com.wodud7308.movieinfo.core.ui.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<T : Any, VH : BaseViewHolder<T, *>>(
    diff: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, VH>(diff) {
    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position).let { data ->
            holder.setData(data)
        }
    }
}
