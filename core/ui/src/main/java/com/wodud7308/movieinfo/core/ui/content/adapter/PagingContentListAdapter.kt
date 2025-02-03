package com.wodud7308.movieinfo.core.ui.content.adapter

import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentViewHolder
import com.wodud7308.movieinfo.core.ui.content.holder.factory.DefaultContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class PagingContentListAdapter(
    eventListener: ContentUiEventListener
) : BasePagingContentAdapter<HolderContentBinding, ContentViewHolder>(
    DefaultContentViewHolderFactory(),
    eventListener
)
