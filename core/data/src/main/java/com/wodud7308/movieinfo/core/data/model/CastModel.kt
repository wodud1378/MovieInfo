package com.wodud7308.movieinfo.core.data.model


import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.model.Cast

data class CastModel(
    @SerializedName("cast_id")
    val castId: Int,
    @SerializedName("character")
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("profile_path")
    val profilePath: String
)

fun CastModel.toDomain(): Cast =
    Cast(
        castId = castId,
        character = character,
        creditId = creditId,
        gender = gender,
        id = id,
        name = name,
        order = order,
        originalName = originalName,
        profilePath = profilePath
    )
