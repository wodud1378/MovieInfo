package com.wodud7308.movieinfo.core.ui.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import com.wodud7308.movieinfo.core.ui.content.holder.BaseContentHolder
import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.factory.ContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

abstract class BasePagingContentAdapter<VB : ViewBinding, VH : BaseContentHolder<VB>>(
    private val factory: ContentViewHolderFactory<VB, VH>,
    private val eventListener: ContentUiEventListener? = null
) : PagingDataAdapter<ContentUiModel, VH>(ContentUiModelDiffCallback()) {
    fun updateFavoriteState(favoriteContent: FavoriteContent) {
        val position = snapshot().items.indexOfFirst { it.content.id == favoriteContent.id }
        if (position >= 0) {
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        peek(position)?.let {
            holder.setData(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        factory.create(LayoutInflater.from(parent.context), parent, eventListener)
}
