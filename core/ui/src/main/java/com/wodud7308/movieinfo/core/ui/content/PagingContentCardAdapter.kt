package com.wodud7308.movieinfo.core.ui.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class PagingContentCardAdapter :
    PagingDataAdapter<Content, ContentCardViewHolder>(ContentDiffCallback()) {
    override fun onBindViewHolder(
        holder: ContentCardViewHolder,
        position: Int,
    ) {
        peek(position)?.let {
            holder.setData(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContentCardViewHolder {
        HolderContentBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            return ContentCardViewHolder(this)
        }
    }
}
