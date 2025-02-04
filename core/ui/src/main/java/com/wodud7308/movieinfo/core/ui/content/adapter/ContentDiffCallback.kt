package com.wodud7308.movieinfo.core.ui.content.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

class ContentUiModelDiffCallback : DiffUtil.ItemCallback<ContentUiModel>() {
    override fun areItemsTheSame(
        oldItem: ContentUiModel,
        newItem: ContentUiModel,
    ): Boolean = oldItem.content.id == newItem.content.id

    override fun areContentsTheSame(
        oldItem: ContentUiModel,
        newItem: ContentUiModel,
    ): Boolean = oldItem == newItem
}
