package com.wodud7308.movieinfo.core.data.model


import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.model.Genre

data class GenreApiModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

fun GenreApiModel.toDomain(): Genre =
    Genre(id, name)
