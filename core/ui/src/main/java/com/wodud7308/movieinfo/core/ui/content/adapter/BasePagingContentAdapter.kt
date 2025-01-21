package com.wodud7308.movieinfo.core.ui.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.holder.BaseContentHolder

abstract class BasePagingContentAdapter<VB : ViewBinding, VH : BaseContentHolder<VB>>(
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val onClickListener: ItemClickListener<Content>? = null
) : PagingDataAdapter<Content, VH>(ContentDiffCallback()) {
    override fun onBindViewHolder(holder: VH, position: Int) {
        peek(position)?.let { data ->
            with(holder) {
                setData(data)
                itemView.setOnClickListener {
                    onClickListener?.onClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return createViewHolder(binding)
    }

    abstract fun createViewHolder(binding: VB): VH
}
