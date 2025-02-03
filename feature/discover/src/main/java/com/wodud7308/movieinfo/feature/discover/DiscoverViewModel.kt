package com.wodud7308.movieinfo.feature.discover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.usecase.content.PagedContentsUseCase
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
class DiscoverViewModel @Inject constructor(
    private val pagedContentsUseCase: PagedContentsUseCase,
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

    private val contentTypeFlow: StateFlow<ContentType>

    init {
        contentTypeFlow = savedStateHandle.getStateFlow(CONTENT_TYPE, ContentType.NowPlaying)

        pagerFlow = combine(
            mediaTypeFlow,
            contentTypeFlow
        ) { mediaType, contentType ->
            mediaType to contentType
        }.flatMapLatest { (mediaType, contentType) ->
            toUiModelFlow(
                pagedContentsUseCase(mediaType, contentType),
                _favoriteContentsFlow
            )
        }.cachedIn(viewModelScope)
    }

    fun setContentType(contentType: ContentType) {
        savedStateHandle[CONTENT_TYPE] = contentType
    }

    companion object {
        const val CONTENT_TYPE = "contentType"
    }
}
