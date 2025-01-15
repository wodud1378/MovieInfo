package com.wodud7308.movieinfo.core.data.model

import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.model.ContentDetail
import com.wodud7308.movieinfo.core.domain.model.Credit

abstract class ContentModel {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("poster_path")
    val posterPath: String? = null

    @SerializedName("backdrop_path")
    val backdropPath: String? = null

    @SerializedName("genres")
    val genres: List<GenreApiModel>? = null

    @SerializedName("homepage")
    val homepage: String = ""

    @SerializedName("overview")
    val overview: String? = null

    @SerializedName("original_language")
    val originalLanguage: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("tagline")
    val tagline: String? = null

    @SerializedName("credits")
    val credits: CreditApiModel? = null

    abstract val title: String
    abstract val releaseDate: String
    abstract val originalTitle: String?

    abstract fun getMediaType(): MediaType
}

fun ContentModel.toDomain(hasDetail: Boolean = false): Content =
    Content(
        mediaType = getMediaType(),
        id = id,
        title = title,
        releaseDate = releaseDate,
        posterPath = posterPath ?: "",
        detail = if (hasDetail) getDetail() else null
    )

private fun ContentModel.getDetail(): ContentDetail =
    ContentDetail(
        overview = overview ?: "",
        backdropPath = backdropPath ?: "",
        genres = genres?.map { it.toDomain() } ?: emptyList(),
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        status = status ?: "",
        tagline = tagline ?: "",
        credits = credits?.toDomain() ?: Credit(emptyList())
    )
