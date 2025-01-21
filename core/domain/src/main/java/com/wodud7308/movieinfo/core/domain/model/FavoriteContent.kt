package com.wodud7308.movieinfo.core.domain.model

import com.wodud7308.movieinfo.core.domain.common.MediaType

data class FavoriteContent(
    val id: Int,
    val mediaType: MediaType,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
)
