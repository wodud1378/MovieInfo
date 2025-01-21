package com.wodud7308.movieinfo.core.ui.content.adapter

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentBindingWrapper
import com.wodud7308.movieinfo.core.ui.content.holder.SearchContentViewHolder
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentSearchBinding

class SearchPagingContentListAdapter(
    onClickListener: ItemClickListener<Content>? = null
) : BasePagingContentAdapter<HolderContentSearchBinding, SearchContentViewHolder>(
    HolderContentSearchBinding::inflate,
    onClickListener
) {
    override fun createViewHolder(binding: HolderContentSearchBinding): SearchContentViewHolder =
        SearchContentViewHolder(ContentBindingWrapper.Search(binding))
}
