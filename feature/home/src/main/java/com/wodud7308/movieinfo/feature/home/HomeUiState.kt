package com.wodud7308.movieinfo.feature.home

import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.model.Movie

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class Success(val movies: PagingData<Movie>) : HomeUiState
}
