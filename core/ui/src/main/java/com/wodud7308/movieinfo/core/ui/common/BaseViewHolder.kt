package com.wodud7308.movieinfo.core.ui.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, VB : ViewBinding>(
    val binding: VB
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun setData(item: T)
}
