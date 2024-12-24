package com.wodud7308.movieinfo.feature.discover

import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.model.Content

sealed interface DiscoverUiState {
    data object Loading : DiscoverUiState
    data object Error : DiscoverUiState
    data class Success(val data: PagingData<Content>) : DiscoverUiState
}
