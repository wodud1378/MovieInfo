package com.wodud7308.movieinfo.core.ui.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.content.holder.BaseContentHolder
import com.wodud7308.movieinfo.core.ui.content.holder.ContentEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.factory.ContentViewHolderFactory

abstract class BasePagingContentAdapter<VB : ViewBinding, VH : BaseContentHolder<VB>>(
    private val factory: ContentViewHolderFactory<VB, VH>,
    private val eventListener: ContentEventListener? = null
) : PagingDataAdapter<Content, VH>(ContentDiffCallback()) {
    override fun onBindViewHolder(holder: VH, position: Int) {
        peek(position)?.let {
            holder.setData(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        factory.create(LayoutInflater.from(parent.context), parent, eventListener)
}
