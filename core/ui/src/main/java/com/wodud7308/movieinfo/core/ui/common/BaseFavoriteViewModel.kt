package com.wodud7308.movieinfo.core.ui.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wodud7308.movieinfo.core.domain.common.MediaType
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

abstract class BaseFavoriteViewModel(
    protected val getFavoriteContentsUseCase: GetFavoriteContentsUseCase,
    protected val insertFavoriteContentUseCase: InsertFavoriteContentUseCase,
    protected val deleteFavoriteContentUseCase: DeleteFavoriteContentUseCase,
    protected val savedStateHandle: SavedStateHandle

) : ViewModel() {
    protected val _favoriteContentsFlow: MutableStateFlow<List<FavoriteContent>> = MutableStateFlow(
        mutableListOf()
    )

    val favoriteContentsFlow: StateFlow<List<FavoriteContent>> = _favoriteContentsFlow.asStateFlow()
    val mediaTypeFlow: StateFlow<MediaType?> =
        savedStateHandle.getStateFlow(MEDIA_TYPE_KEY, null)

    init {
        viewModelScope.launch {
            mediaTypeFlow.collectLatest { mediaType ->
                getFavoriteContentsUseCase(mediaType).collectLatest { result ->
                    _favoriteContentsFlow.value = result.getOrNull() ?: emptyList()
                }
            }
        }
    }

    fun setMediaType(mediaType: MediaType?) {
        savedStateHandle[MEDIA_TYPE_KEY] = mediaType
    }

    fun toggleFavorite(item: ContentUiModel) {
        viewModelScope.launch {
            val list = _favoriteContentsFlow.value.toMutableList()
            val found = list.firstOrNull { it.id == item.content.id }
            val favorite: FavoriteContent
            val useCase: (FavoriteContent) -> Flow<Result<FavoriteContent>>
            val onSuccess: suspend (FavoriteContent) -> Unit
            if (found == null) {
                favorite = item.toFavorite()
                useCase = insertFavoriteContentUseCase::invoke
                onSuccess = { f ->
                    list.add(f)
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
                    _favoriteContentsFlow.value = list
                }
            }
        }
    }

    companion object {
        private const val MEDIA_TYPE_KEY = "mediaType"
    }
}
