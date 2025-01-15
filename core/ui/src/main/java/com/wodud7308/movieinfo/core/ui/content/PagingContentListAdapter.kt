package com.wodud7308.movieinfo.core.ui.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class PagingContentListAdapter(
    private val onClickListener: ItemClickListener<Content>? = null
) :
    PagingDataAdapter<Content, ContentViewHolder>(ContentDiffCallback()) {
    override fun onBindViewHolder(
        holder: ContentViewHolder,
        position: Int,
    ) {
        peek(position)?.let { data ->
            with(holder) {
                setData(data)
                itemView.setOnClickListener {
                    onClickListener?.onClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContentViewHolder {
        HolderContentBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            return ContentViewHolder(this)
        }
    }
}
