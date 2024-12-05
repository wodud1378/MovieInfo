package com.wodud7308.movieinfo.core.data.model

import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content

abstract class ContentModel {
    @SerializedName("adult")
    val adult: Boolean = false

    @SerializedName("genre_ids")
    val genreIds: List<Int> = emptyList()

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("original_language")
    val originalLanguage: String = ""

    @SerializedName("overview")
    val overview: String = ""

    @SerializedName("popularity")
    val popularity: Double = 0.0

    @SerializedName("poster_path")
    val posterPath: String = ""

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0

    @SerializedName("vote_count")
    val voteCount: Int = 0

    @SerializedName("credit")
    val credit: CreditApiModel? = null

    abstract val mediaType: MediaType
    abstract val title: String
    abstract val originalTitle: String
    abstract val releaseDate: String
    abstract val backdropPath: String?
}

fun ContentModel.toDomain(): Content =
    Content(
        id = id,
        genreIds = genreIds,
        adult = adult,
        title = title,
        releaseDate = releaseDate,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
