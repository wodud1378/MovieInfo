package com.wodud7308.movieinfo.core.ui.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<T : Any, VH : BaseViewHolder<T, *>>(
    diff: DiffUtil.ItemCallback<T>,
    private val itemClickListener: ItemClickListener<T>? = null
) : ListAdapter<T, VH>(diff) {
    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder) {
            getItem(position).let { data ->
                itemView.setOnClickListener {
                    itemClickListener?.onClick(data)
                }

                holder.setData(data)
            }
        }
    }
}
