package com.wodud7308.movieinfo.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.wodud7308.movieinfo.core.domain.usecase.content.SearchPagedContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.DeleteFavoriteContentUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.GetFavoriteContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.InsertFavoriteContentUseCase
import com.wodud7308.movieinfo.core.ui.common.BaseFavoriteViewModel
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPagedContentsUseCase: SearchPagedContentsUseCase,
    getFavoriteContentsUseCase: GetFavoriteContentsUseCase,
    insertFavoriteContentUseCase: InsertFavoriteContentUseCase,
    deleteFavoriteContentUseCase: DeleteFavoriteContentUseCase,
    savedStateHandle: SavedStateHandle
) : BaseFavoriteViewModel(
    getFavoriteContentsUseCase,
    insertFavoriteContentUseCase,
    deleteFavoriteContentUseCase,
    savedStateHandle
) {
    val queryFlow: StateFlow<String> = savedStateHandle.getStateFlow(QUERY_KEY, "")

    val pagerFlow: Flow<PagingData<ContentUiModel>> = combine(
        mediaTypeFlow,
        queryFlow,
        _favoriteContentsFlow,
    ) { mediaType, query, favorites ->
        Triple(mediaType, query, favorites)
    }.flatMapLatest { (mediaType, query, favorites) ->
        searchPagedContentsUseCase(mediaType, query).map { result ->
            result.map { content ->
                ContentUiModel(content, favorites.any { it.id == content.id })
            }
        }
    }.cachedIn(viewModelScope)

    fun setQuery(query: String) {
        savedStateHandle[QUERY_KEY] = query
    }

    companion object {
        const val QUERY_KEY = "query"
    }
}
