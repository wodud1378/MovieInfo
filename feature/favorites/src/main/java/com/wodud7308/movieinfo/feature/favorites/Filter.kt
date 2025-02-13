package com.wodud7308.movieinfo.feature.favorites

import com.wodud7308.movieinfo.core.domain.common.MediaType

enum class Filter(val mediaType: MediaType?) {
    All(null),
    Movie(MediaType.Movie),
    Tv(MediaType.Tv)
}

fun MediaType?.toFilter(): Filter {
    return Filter.entries.find { it.mediaType == this }!!
}
