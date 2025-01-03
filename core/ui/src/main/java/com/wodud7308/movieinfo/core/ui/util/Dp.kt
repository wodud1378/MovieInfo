package com.wodud7308.movieinfo.core.ui.util

import android.content.Context

fun Int.toPixel(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}
