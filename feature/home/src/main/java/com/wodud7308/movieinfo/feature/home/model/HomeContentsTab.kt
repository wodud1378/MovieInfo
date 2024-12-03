package com.wodud7308.movieinfo.feature.home.model

import androidx.annotation.StringRes

data class HomeContentsTab(
    val type: Enum<*>,
    @StringRes
    val nameId: Int
)
