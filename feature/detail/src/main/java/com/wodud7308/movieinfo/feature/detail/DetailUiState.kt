package com.wodud7308.movieinfo.feature.detail

import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data object Error : DetailUiState
    data class ShowData(val data: ContentUiModel) : DetailUiState
}
