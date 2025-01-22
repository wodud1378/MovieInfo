package com.wodud7308.movieinfo.core.ui.content.adapter

import com.wodud7308.movieinfo.core.ui.content.holder.ContentEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.SearchContentViewHolder
import com.wodud7308.movieinfo.core.ui.content.holder.factory.SearchContentViewHolderFactory
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentSearchBinding

class SearchPagingContentListAdapter(
    eventListener: ContentEventListener
) : BasePagingContentAdapter<HolderContentSearchBinding, SearchContentViewHolder>(
    SearchContentViewHolderFactory(),
    eventListener
)
