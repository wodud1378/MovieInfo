package com.wodud7308.movieinfo.core.ui.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.BaseListAdapter
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentBindingWrapper
import com.wodud7308.movieinfo.core.ui.content.holder.ContentEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentViewHolder
import com.wodud7308.movieinfo.core.ui.content.holder.factory.DefaultContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class ContentListAdapter(
    private val eventListener: ContentEventListener
) : BaseListAdapter<Content, ContentViewHolder>
    (
    ContentDiffCallback(),
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
