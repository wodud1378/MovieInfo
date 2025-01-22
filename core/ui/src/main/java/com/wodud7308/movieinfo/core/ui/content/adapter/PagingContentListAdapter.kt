package com.wodud7308.movieinfo.core.ui.content.adapter

import com.wodud7308.movieinfo.core.ui.content.holder.ContentEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentViewHolder
import com.wodud7308.movieinfo.core.ui.content.holder.factory.DefaultContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class PagingContentListAdapter(
    eventListener: ContentEventListener
) : BasePagingContentAdapter<HolderContentBinding, ContentViewHolder>(
    DefaultContentViewHolderFactory(),
    eventListener
)
