package com.wodud7308.movieinfo.core.navigation

import android.content.Context

sealed class DeepLink(val addressRes: Int) {
    data class Detail(val type: String, val id: Int) : DeepLink(R.string.detail_deep_link_uri)
}

fun DeepLink.get(context: Context) = context.getString(addressRes)
