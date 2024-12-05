package com.wodud7308.movieinfo.core.data.model


import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.common.MediaType

data class TvShowApiModel(
    @SerializedName("name")
    override val title: String,
    @SerializedName("original_name")
    override val originalTitle: String,
    @SerializedName("first_air_date")
    override val releaseDate: String
) : ContentModel() {
    override val backdropPath: String? = null
    override val mediaType: MediaType = MediaType.Tv
}
