package com.wodud7308.movieinfo.feature.discover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.usecase.content.PagedContentsUseCase
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val pagedContentsUseCase: PagedContentsUseCase,
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
    private val contentTypeFlow: StateFlow<ContentType> =
        savedStateHandle.getStateFlow(CONTENT_TYPE_KEY, ContentType.NowPlaying)

    val pagerFlow: Flow<PagingData<ContentUiModel>> = combine(
        mediaTypeFlow,
        contentTypeFlow,
        _favoriteContentsFlow,
    ) { mediaType, contentType, favorites ->
        Triple(mediaType, contentType, favorites)
    }.flatMapLatest { (mediaType, contentType, favorites) ->
        if (mediaType != null) {
            pagedContentsUseCase(mediaType, contentType).map { result ->
                result.map { content ->
                    ContentUiModel(content, favorites.any { it.id == content.id })
                }
            }
        } else {
            flowOf(PagingData.empty())
        }

    }.cachedIn(viewModelScope)

    fun setContentType(contentType: ContentType) {
        savedStateHandle[CONTENT_TYPE_KEY] = contentType
    }

    companion object {
        const val CONTENT_TYPE_KEY = "contentType"
    }
}
