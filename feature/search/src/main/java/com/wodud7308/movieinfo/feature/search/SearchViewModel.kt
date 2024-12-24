package com.wodud7308.movieinfo.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.usecase.SearchPagedContentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPagedContentsUseCase: SearchPagedContentsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val pagerFlow: Flow<PagingData<Content>> = buildDataFlow()

    fun setQuery(query: String) {
        savedStateHandle[QUERY] = query
    }

    fun setMediaType(mediaType: MediaType) {
        savedStateHandle[MEDIA_TYPE] = mediaType
    }

    private fun buildDataFlow(): Flow<PagingData<Content>> = combine(
        savedStateHandle.getStateFlow(MEDIA_TYPE, MediaType.Movie),
        savedStateHandle.getStateFlow(QUERY, ""),
    ) { mediaType, query ->
        mediaType to query
    }.flatMapLatest { (mediaType, query) ->
        searchPagedContentsUseCase(mediaType, query)
    }.cachedIn(viewModelScope)

    companion object {
        const val QUERY = "query"
        const val MEDIA_TYPE = "mediaType"
    }
}
