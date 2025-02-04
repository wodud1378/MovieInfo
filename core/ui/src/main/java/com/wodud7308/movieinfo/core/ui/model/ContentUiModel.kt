package com.wodud7308.movieinfo.core.ui.model

import com.wodud7308.movieinfo.core.domain.model.Content

data class ContentUiModel(
    val content: Content,
    val isFavorite: Boolean
)
