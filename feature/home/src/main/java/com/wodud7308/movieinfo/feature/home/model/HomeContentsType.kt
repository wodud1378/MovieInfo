package com.wodud7308.movieinfo.feature.home.model

import androidx.annotation.StringRes
import com.wodud7308.movieinfo.feature.home.R

enum class HomeContentsType(
    @StringRes
    val title:Int
) {
    Trending(R.string.trending),
    Popular(R.string.popular)
}
