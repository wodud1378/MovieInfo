package com.wodud7308.movieinfo.core.ui.content.holder.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wodud7308.movieinfo.core.ui.content.holder.ContentBindingWrapper
import com.wodud7308.movieinfo.core.ui.content.holder.ContentEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.SearchContentViewHolder
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentSearchBinding

class SearchContentViewHolderFactory :
    ContentViewHolderFactory<HolderContentSearchBinding, SearchContentViewHolder> {
    override fun create(
        inflater: LayoutInflater,
        parent: ViewGroup,
        contentEventListener: ContentEventListener?
    ): SearchContentViewHolder {
        val binding = HolderContentSearchBinding.inflate(inflater, parent, false)
        val wrapper = ContentBindingWrapper.Search(binding)

        return SearchContentViewHolder(wrapper, contentEventListener)
    }
}
