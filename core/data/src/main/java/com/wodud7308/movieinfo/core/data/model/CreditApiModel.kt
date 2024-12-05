package com.wodud7308.movieinfo.core.data.model


import com.google.gson.annotations.SerializedName

data class CreditApiModel(
    @SerializedName("cast")
    val cast: List<CastModel>
)
