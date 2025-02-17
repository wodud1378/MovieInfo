package com.wodud7308.movieinfo.core.navigation

import android.content.Context
import android.net.Uri

sealed interface DeepLink {
    fun buildUri(): Uri

    data class Detail(val context: Context, val mediaType: String, val id: Int) : DeepLink {
        override fun buildUri(): Uri {
            return Uri
                .parse(context.getString(R.string.detail_deep_link_uri))
                .buildUpon()
                .appendPath(mediaType)
                .appendPath(id.toString())
                .build()
        }
    }
}
