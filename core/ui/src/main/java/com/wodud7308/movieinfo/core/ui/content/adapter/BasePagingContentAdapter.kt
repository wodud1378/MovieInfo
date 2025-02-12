package com.wodud7308.movieinfo.core.ui.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import com.wodud7308.movieinfo.core.ui.content.holder.BaseContentHolder
import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.factory.ContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

abstract class BasePagingContentAdapter<VB : ViewBinding, VH : BaseContentHolder<VB>>(
    private val factory: ContentViewHolderFactory<VB, VH>,
    private val eventListener: ContentUiEventListener? = null
) : PagingDataAdapter<ContentUiModel, VH>(ContentUiModelDiffCallback()) {
    private var lastFavoriteIds: List<Int>? = null

    fun updateFavorites(favorites: List<FavoriteContent>) {
        val current = favorites.map { it.id }
        val updated: List<Pair<Int, Boolean>> = if (lastFavoriteIds != null) {
            val last = lastFavoriteIds!!
            val removed = last.filter { it !in current }.map {
                it to false
            }
            val added = current.filter { it !in last }.map {
                it to true
            }
            removed + added
        } else {
            current.map { it to true }
        }

        val items = snapshot().items

        updated.forEach { (id, isFavorite) ->
            val position = items.indexOfFirst { it.content.id == id }
            if (position != -1) {
                notifyItemChanged(position, isFavorite)
            }
        }

        lastFavoriteIds = current
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let {
            holder.setData(it)
            holder.updateFavoriteIcon(it.isFavorite)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val isFavorite = payloads[0] as? Boolean
            isFavorite?.let { holder.updateFavoriteIcon(it) }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        factory.create(LayoutInflater.from(parent.context), parent, eventListener)
}
