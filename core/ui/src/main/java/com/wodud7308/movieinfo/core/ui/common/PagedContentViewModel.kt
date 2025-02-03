package com.wodud7308.movieinfo.core.ui.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import com.wodud7308.movieinfo.core.domain.usecase.favorite.DeleteFavoriteContentUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.GetFavoriteContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.InsertFavoriteContentUseCase
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.util.toFavorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class PagedContentViewModel(
    private val getFavoriteContentsUseCase: GetFavoriteContentsUseCase,
    private val insertFavoriteContentUseCase: InsertFavoriteContentUseCase,
    private val deleteFavoriteContentUseCase: DeleteFavoriteContentUseCase,
    protected val savedStateHandle: SavedStateHandle
) : ViewModel() {
    protected val _favoriteContentsFlow: MutableStateFlow<List<FavoriteContent>> = MutableStateFlow(
        mutableListOf()
    )

    abstract val pagerFlow: Flow<PagingData<ContentUiModel>>
    val favoriteContentsFlow: StateFlow<List<FavoriteContent>> = _favoriteContentsFlow.asStateFlow()
    val mediaTypeFlow: StateFlow<MediaType> =
        savedStateHandle.getStateFlow(MEDIA_TYPE, MediaType.Movie)

    init {
        viewModelScope.launch {
            mediaTypeFlow.collectLatest { mediaType ->
                getFavoriteContentsUseCase(mediaType).collectLatest { result ->
                    _favoriteContentsFlow.value = result.getOrNull() ?: emptyList()
                }
            }
        }
    }

    fun setMediaType(mediaType: MediaType) {
        savedStateHandle[MEDIA_TYPE] = mediaType
    }

    fun toggleFavorite(content: Content) {
        viewModelScope.launch {
            val list = _favoriteContentsFlow.value.toMutableList()
            val found = list.firstOrNull { it.id == content.id }
            val favorite: FavoriteContent
            val useCase: (FavoriteContent) -> Flow<Result<FavoriteContent>>
            val onSuccess: suspend (FavoriteContent) -> Unit
            if (found == null) {
                favorite = content.toFavorite()
                useCase = insertFavoriteContentUseCase::invoke
                onSuccess = { f ->
                    list.add(f)
                    _favoriteContentsFlow.value = list
                }
            } else {
                favorite = found
                useCase = deleteFavoriteContentUseCase::invoke
                onSuccess = { f ->
                    list.remove(f)
                }
            }

            useCase(favorite).collectLatest { result ->
                result.onSuccess {
                    onSuccess(it)
                }
            }
        }
    }

    companion object {
        private const val MEDIA_TYPE = "mediaType"
    }
}
