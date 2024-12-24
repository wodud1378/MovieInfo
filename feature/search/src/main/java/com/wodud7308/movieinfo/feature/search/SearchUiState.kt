package com.wodud7308.movieinfo.feature.search

import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.model.Content

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data object Error : SearchUiState
    data class Success(val data: PagingData<Content>) : SearchUiState
}
