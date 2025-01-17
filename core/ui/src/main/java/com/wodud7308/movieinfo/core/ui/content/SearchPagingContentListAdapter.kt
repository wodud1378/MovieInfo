package com.wodud7308.movieinfo.core.ui.content

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentSearchBinding

class SearchPagingContentListAdapter(
    onClickListener: ItemClickListener<Content>? = null
) : BasePagingContentAdapter<HolderContentSearchBinding, SearchContentViewHolder>(
    HolderContentSearchBinding::inflate,
    ::SearchContentViewHolder,
    onClickListener
)
