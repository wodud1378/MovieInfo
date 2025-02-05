package com.wodud7308.movieinfo.feature.home.util

import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.common.PopularContentType

fun PopularContentType.toMediaType(): MediaType = when(this) {
    PopularContentType.Movie -> MediaType.Movie
    PopularContentType.Tv -> MediaType.Tv
}
