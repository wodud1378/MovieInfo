package com.wodud7308.movieinfo.feature.home

import com.wodud7308.movieinfo.feature.home.model.HomeUiModel

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class ShowData(
        val uiModel: HomeUiModel,
    ) : HomeUiState
}
