package com.wodud7308.movieinfo.core.domain.model

import com.wodud7308.movieinfo.core.domain.common.MediaType

data class Content(
    val mediaType: MediaType,
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val detail: ContentDetail?,
)

data class ContentDetail(
    val overview: String,
    val backdropPath: String,
    val genres: List<Genre>,
    val originalLanguage: String,
    val originalTitle: String,
    val status: String,
    val tagline: String,
    val credits: Credit?
)
