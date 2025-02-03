package com.wodud7308.movieinfo.core.ui.content.adapter

import com.wodud7308.movieinfo.core.ui.content.holder.ContentUiEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.SearchContentViewHolder
import com.wodud7308.movieinfo.core.ui.content.holder.factory.SearchContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentSearchBinding

class SearchPagingContentListAdapter(
    eventListener: ContentUiEventListener
) : BasePagingContentAdapter<HolderContentSearchBinding, SearchContentViewHolder>(
    SearchContentViewHolderFactory(),
    eventListener
)
