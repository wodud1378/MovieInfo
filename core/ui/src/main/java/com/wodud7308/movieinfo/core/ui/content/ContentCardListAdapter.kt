package com.wodud7308.movieinfo.core.ui.content

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.BaseListAdapter
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class ContentCardListAdapter :
    BaseListAdapter<Content, ContentCardViewHolder>(
        ContentDiffCallback(),
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentCardViewHolder {
        HolderContentBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            return ContentCardViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ContentCardViewHolder, position: Int) {
        with(holder) {
            getItem(position).let { data ->
                setData(data)
                itemView.setOnClickListener {
                    itemClickListener?.onClick(data)
                }
            }
        }
    }
}
