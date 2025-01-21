package com.wodud7308.movieinfo.core.ui.content.adapter

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentBindingWrapper
import com.wodud7308.movieinfo.core.ui.content.holder.ContentViewHolder
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class PagingContentListAdapter(
    onClickListener: ItemClickListener<Content>? = null,
    favoriteStateListener: FavoriteStateListener
) : BasePagingContentAdapter<HolderContentBinding, ContentViewHolder>(
    HolderContentBinding::inflate,
    onClickListener
) {
    override fun createViewHolder(binding: HolderContentBinding): ContentViewHolder =
        ContentViewHolder(ContentBindingWrapper.Default(binding))
}
