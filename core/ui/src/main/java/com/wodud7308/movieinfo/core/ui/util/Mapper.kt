package com.wodud7308.movieinfo.core.ui.util

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

fun FavoriteContent.toContentUiModel(): ContentUiModel =
    ContentUiModel(
        Content(
            mediaType = mediaType,
            id = id,
            title = title,
            releaseDate = releaseDate,
            posterPath = posterPath,
            detail = null
        ), true
    )

fun ContentUiModel.toFavorite(): FavoriteContent =
    with(content) {
        FavoriteContent(
            mediaType = mediaType,
            id = id,
            title = title,
            releaseDate = releaseDate,
            posterPath = posterPath,
        )
    }
