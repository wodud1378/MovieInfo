package com.wodud7308.movieinfo.core.domain.model

data class Cast(
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val originalName: String,
    val profilePath: String
)
