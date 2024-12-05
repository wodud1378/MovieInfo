package com.wodud7308.movieinfo.core.data.model

import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.model.Content

data class ContentListApiModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ContentModel>,
)

fun ContentListApiModel.toDomain(): List<Content> =
    results.mapNotNull {
        try {
            it.toDomain()
        } catch (e: Exception) {
            null
        }
    }
