package com.wodud7308.movieinfo.feature.home.model

import androidx.annotation.StringRes
import com.wodud7308.movieinfo.core.ui.R

enum class HomeContentsType(
    @StringRes
    val title:Int
) {
    Trending(com.wodud7308.movieinfo.feature.home.R.string.trending),
    Popular(R.string.popular)
}
