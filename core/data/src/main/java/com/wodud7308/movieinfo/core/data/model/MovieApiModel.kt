package com.wodud7308.movieinfo.core.data.model


import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.common.MediaType

data class MovieApiModel(
    @SerializedName("title")
    override val title: String,
    @SerializedName("original_title")
    override val originalTitle: String?,
    @SerializedName("release_date")
    override val releaseDate: String,
) : ContentModel() {
    override fun getMediaType(): MediaType = MediaType.Movie
}
