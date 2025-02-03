package com.wodud7308.movieinfo.core.ui.content.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wodud7308.movieinfo.core.domain.model.Content
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

    override fun getChangePayload(oldItem: ContentUiModel, newItem: ContentUiModel): Any? {
        if(oldItem.isFavorite != newItem.isFavorite) {
            return favoriteChangePayload
        }

        return null
    }

    companion object {
        val favoriteChangePayload = "is_favorite_changed"
    }
}
