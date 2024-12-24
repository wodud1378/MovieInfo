package com.wodud7308.movieinfo.core.navigation

import android.content.Context
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions

fun NavController.navigate(
    context: Context,
    deepLink: DeepLink,
    popUpTo: Boolean = false
) {
    val builder = NavOptions.Builder()

    if(popUpTo) {
        builder.setPopUpTo(graph.startDestinationId, true)
    }

    navigate(
        NavDeepLinkRequest.Builder
            .fromUri(deepLink.get(context).toUri())
            .build()
    )
}
