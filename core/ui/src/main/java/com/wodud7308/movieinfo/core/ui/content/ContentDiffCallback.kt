package com.wodud7308.movieinfo.core.ui.content

import androidx.recyclerview.widget.DiffUtil
import com.wodud7308.movieinfo.core.domain.model.Content

class ContentDiffCallback : DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(
        oldItem: Content,
        newItem: Content,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Content,
        newItem: Content,
    ): Boolean = oldItem == newItem
}
