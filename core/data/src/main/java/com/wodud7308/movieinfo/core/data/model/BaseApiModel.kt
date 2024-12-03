package com.wodud7308.movieinfo.core.data.model

abstract class BaseApiModel(
    open val adult: Boolean,
    open val genreIds: List<Int>,
    open val id: Int,
    open val mediaType: String,
    open val originalLanguage: String,
    open val overview: String,
    open val popularity: Double,
    open val posterPath: String,
    open val voteAverage: Double,
    open val voteCount: Int
)
