package com.wodud7308.movieinfo.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wodud7308.movieinfo.core.domain.usecase.content.SearchPagedContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.DeleteFavoriteContentUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.GetFavoriteContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.InsertFavoriteContentUseCase
import com.wodud7308.movieinfo.core.ui.common.PagedContentViewModel
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.model.toUiModelFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPagedContentsUseCase: SearchPagedContentsUseCase,
    getFavoriteContentsUseCase: GetFavoriteContentsUseCase,
    insertFavoriteContentUseCase: InsertFavoriteContentUseCase,
    deleteFavoriteContentUseCase: DeleteFavoriteContentUseCase,
    savedStateHandle: SavedStateHandle
) : PagedContentViewModel(
    getFavoriteContentsUseCase,
    insertFavoriteContentUseCase,
    deleteFavoriteContentUseCase,
    savedStateHandle
) {
    override val pagerFlow: Flow<PagingData<ContentUiModel>>
    val queryFlow: StateFlow<String>

    init {
        queryFlow = savedStateHandle.getStateFlow(QUERY, "")

        pagerFlow = combine(
            mediaTypeFlow,
            queryFlow
        ) { mediaType, query ->
            mediaType to query
        }.flatMapLatest { (mediaType, query) ->
            toUiModelFlow(
                searchPagedContentsUseCase(mediaType, query),
                _favoriteContentsFlow
            )
        }.cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        savedStateHandle[QUERY] = query
    }

    companion object {
        const val QUERY = "query"
    }
}
