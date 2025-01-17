package com.wodud7308.movieinfo.core.navigation

import androidx.navigation.NavController

fun NavController.navigateToDeepLink(deepLink: DeepLink) {
    val uri = deepLink.buildUri()

    navigate(uri)
}
