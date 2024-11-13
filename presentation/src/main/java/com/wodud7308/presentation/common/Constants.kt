package com.wodud7308.presentation.common

object Constants {
    const val BASE_URL = "https://image.tmdb.org/t/p/"
    const val SIZE_92 = "w92"
    const val SIZE_154 = "w154"
    const val SIZE_185 = "w185"
    const val SIZE_342 = "w342"
    const val SIZE_500 = "w500"
    const val SIZE_780 = "w780"
}

enum class PosterSize(
    val size: Int,
) {
    W92(92),
    W154(154),
    W185(185),
    W342(342),
    W500(500),
    W780(780),
}

class PosterPath {
    companion object {
        fun urlOf(
            path: String,
            posterSize: PosterSize,
        ): String = "$BASE_URL$path${posterSize.size}"

        private const val BASE_URL = "https://image.tmdb.org/t/p/"
    }
}
