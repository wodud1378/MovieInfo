package com.wodud7308.movieinfo.core.ui.content.holder.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wodud7308.movieinfo.core.ui.content.holder.ContentBindingWrapper
import com.wodud7308.movieinfo.core.ui.content.holder.ContentEventListener
import com.wodud7308.movieinfo.core.ui.content.holder.ContentViewHolder
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class DefaultContentViewHolderFactory :
    ContentViewHolderFactory<HolderContentBinding, ContentViewHolder> {
    override fun create(
        inflater: LayoutInflater,
        parent: ViewGroup,
        contentEventListener: ContentEventListener?
    ): ContentViewHolder {
        val binding = HolderContentBinding.inflate(inflater, parent, false)
        val wrapper = ContentBindingWrapper.Default(binding)

        return ContentViewHolder(wrapper, contentEventListener)
    }
}
