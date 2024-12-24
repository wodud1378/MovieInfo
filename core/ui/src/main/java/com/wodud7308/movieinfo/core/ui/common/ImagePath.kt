package com.wodud7308.movieinfo.core.ui.common

class ImagePath {
    companion object {
        fun urlOf(
            path: String,
            imageSize: ImageSize,
        ): String = "$BASE_URL/w${imageSize.size}/$path"

        private const val BASE_URL = "https://image.tmdb.org/t/p"
    }
}
