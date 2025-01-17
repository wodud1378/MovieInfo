package com.wodud7308.movieinfo.core.ui.content

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class PagingContentListAdapter(
    onClickListener: ItemClickListener<Content>? = null
) : BasePagingContentAdapter<HolderContentBinding, ContentViewHolder>(
    HolderContentBinding::inflate,
    ::ContentViewHolder,
    onClickListener
)
