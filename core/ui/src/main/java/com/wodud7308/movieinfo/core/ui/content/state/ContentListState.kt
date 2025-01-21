package com.wodud7308.movieinfo.core.ui.content.state

import com.wodud7308.movieinfo.core.domain.model.Content

sealed interface ContentListState {
    data object Loading : ContentListState
    data object Error : ContentListState
    data class Success(val contents: List<Content>) : ContentListState
}
