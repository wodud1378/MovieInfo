package com.wodud7308.movieinfo.core.data.model


import com.google.gson.annotations.SerializedName
import com.wodud7308.movieinfo.core.domain.model.Credit

data class CreditApiModel(
    @SerializedName("cast")
    val casts: List<CastApiModel>
)

fun CreditApiModel.toDomain(): Credit =
    Credit(
        casts.mapNotNull {
            try {
                it.toDomain()
            } catch (e: Exception) {
                null
            }
        }
    )
