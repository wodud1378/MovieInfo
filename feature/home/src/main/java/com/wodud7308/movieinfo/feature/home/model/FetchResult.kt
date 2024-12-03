package com.wodud7308.movieinfo.feature.home.model

import com.wodud7308.movieinfo.core.ui.content.ContentListState

data class FetchResult(
    val tab: Enum<*>,
    val state: ContentListState
)
