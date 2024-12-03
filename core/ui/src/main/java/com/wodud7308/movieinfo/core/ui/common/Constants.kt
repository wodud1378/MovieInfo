package com.wodud7308.movieinfo.core.ui.common

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
        ): String = "$BASE_URL/w${posterSize.size}/$path"

        private const val BASE_URL = "https://image.tmdb.org/t/p"
    }
}
