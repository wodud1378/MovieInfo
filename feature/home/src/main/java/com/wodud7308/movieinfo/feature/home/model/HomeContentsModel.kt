package com.wodud7308.movieinfo.feature.home.model

import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

data class HomeContentsModel(
    val type: HomeContentsType,
    val tab: Enum<*>,
    val list: List<ContentUiModel>,
)
