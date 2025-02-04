package com.wodud7308.movieinfo.feature.favorites

import androidx.lifecycle.SavedStateHandle
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.usecase.favorite.DeleteFavoriteContentUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.GetFavoriteContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.InsertFavoriteContentUseCase
import com.wodud7308.movieinfo.core.ui.common.BaseFavoriteViewModel
import com.wodud7308.movieinfo.core.ui.content.state.ContentListState
import com.wodud7308.movieinfo.core.ui.util.stateInViewModel
import com.wodud7308.movieinfo.core.ui.util.toContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
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
    val contentListState: StateFlow<ContentListState> = buildContentsFlow()
        .stateInViewModel(this, ContentListState.Loading)

    private val selectedTabFlow: StateFlow<MediaType?> = buildTabFlow()
        .stateInViewModel(this, null)

    fun setMediaType(mediaType: MediaType?) {
        if (mediaType == null) {
            savedStateHandle[MEDIA_TYPE_SELECTED_KEY] = false
        } else {
            savedStateHandle[MEDIA_TYPE_SELECTED_KEY] = true
            super.setMediaType(mediaType)
        }
    }

    private fun buildTabFlow(): Flow<MediaType?> {
        return combine(
            savedStateHandle.getStateFlow(MEDIA_TYPE_SELECTED_KEY, false),
            mediaTypeFlow
        ) { selected, mediaType ->
            selected to mediaType
        }.map { (selected, mediaType) ->
            if (!selected) null else mediaType
        }
    }

    private fun buildContentsFlow(): Flow<ContentListState> =
        mediaTypeFlow.flatMapLatest { mediaType ->
            getFavoriteContentsUseCase.invoke(mediaType).map { data ->
                ContentListState.Success(data.getOrThrow().map {
                    it.toContent()
                })
            }.onStart {
                ContentListState.Loading
            }.catch {
                ContentListState.Error
            }
        }

    companion object {
        private const val MEDIA_TYPE_SELECTED_KEY = "mediaType"
    }
}
