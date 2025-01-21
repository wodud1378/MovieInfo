package com.wodud7308.movieinfo.core.ui.util

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent

fun FavoriteContent.toContent(): Content =
    Content(
        mediaType = mediaType,
        id = id,
        title = title,
        releaseDate = releaseDate,
        posterPath = posterPath,
        detail = null
    )
