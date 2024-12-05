package com.wodud7308.movieinfo.core.domain.model

data class Content(
    val id: Int,
    val genreIds: List<Int>,
    val adult: Boolean,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
)
