package com.wodud7308.movieinfo.feature.detail

import com.wodud7308.movieinfo.core.domain.model.Content

sealed interface DetailUiState {
    data object Loading: DetailUiState
    data object Error: DetailUiState
    data class ShowContent(val content: Content) : DetailUiState
}
