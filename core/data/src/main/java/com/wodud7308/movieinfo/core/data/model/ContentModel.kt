package com.wodud7308.movieinfo.core.data.model

import com.wodud7308.movieinfo.core.domain.model.Content

data class ContentModel(
    val adult: Boolean,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
) {
    constructor(model: MovieApiModel) : this(
        model.adult,
        model.genreIds,
        model.id,
        model.originalLanguage,
        model.originalTitle,
        model.overview,
        model.popularity,
        model.posterPath,
        model.releaseDate,
        model.title,
        model.voteAverage,
        model.voteCount
    )

    constructor(model: TvShowApiModel) : this(
        model.adult,
        model.genreIds,
        model.id,
        model.originalLanguage,
        model.originalName,
        model.overview,
        model.popularity,
        model.posterPath,
        model.firstAirDate,
        model.name,
        model.voteAverage,
        model.voteCount
    )
}

fun ContentModel.toDomain(): Content =
    Content(
        adult,
        genreIds,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        voteAverage,
        voteCount
    )
