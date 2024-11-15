package com.wodud7308.movieinfo.core.domain.model

data class MovieList(
    val page: Int,
    val results: List<Movie>,
)

data class MovieListInDates(
    val dates: DateRange,
    val page: Int,
    val results: List<Movie>,
)

data class DateRange(
    val min: String,
    val max: String,
)

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)
