package com.wodud7308.movieinfo.core.ui.util

import android.content.Context
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.ui.R

fun MediaType.getString(context: Context): String {
    val id = when (this) {
        MediaType.Movie -> R.string.movie
        MediaType.Tv -> R.string.tv
    }

    return context.getString(id)
}

fun ContentType.getString(context: Context): String {
    val id = when (this) {
        ContentType.NowPlaying -> R.string.now_playing
        ContentType.Upcoming -> R.string.upcoming
        ContentType.Popular -> R.string.popular
        ContentType.TopRated -> R.string.top_rated
    }

    return context.getString(id)
}
