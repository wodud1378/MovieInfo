package com.wodud7308.movieinfo.core.ui.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wodud7308.movieinfo.core.ui.common.BaseListAdapter
import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentViewHolder
import com.wodud7308.movieinfo.core.ui.content.holder.factory.DefaultContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

class ContentListAdapter(
    private val eventListener: ContentUiEventListener? = null
) : BaseListAdapter<ContentUiModel, ContentViewHolder>
    (
    ContentUiModelDiffCallback(),
) {
    private val factory = DefaultContentViewHolderFactory()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return factory.create(
            LayoutInflater.from(parent.context),
            parent,
            eventListener
        )
    }
}
