package com.wodud7308.movieinfo.core.ui.common

interface ImageSize {
    val size: Int
}

enum class PosterSize(
    override val size: Int,
) : ImageSize {
    W154(154),
    W185(185),
}

enum class BackdropSize(
    override val size: Int,
) : ImageSize {
    W300(300),
}

enum class ProfileSize(
    override val size: Int
) : ImageSize {
    W185(185)
}
