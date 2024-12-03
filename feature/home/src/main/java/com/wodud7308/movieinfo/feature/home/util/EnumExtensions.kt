package com.wodud7308.movieinfo.feature.home.util

import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.ui.R

fun Enum<*>.getTabStringId(): Int {
    return when (this) {
        is PopularContentType -> {
            when (this) {
                PopularContentType.Movie -> R.string.movie
                PopularContentType.Tv -> R.string.tv
            }
        }

        is TrendingContentType -> {
            when (this) {
                TrendingContentType.Today -> com.wodud7308.movieinfo.feature.home.R.string.today
                TrendingContentType.ThisWeek -> com.wodud7308.movieinfo.feature.home.R.string.this_week
            }
        }

        else -> throw IllegalArgumentException("Unsupported Enum type: ${this::class.simpleName}")
    }
}
