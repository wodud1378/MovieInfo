package com.wodud7308.movieinfo.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.usecase.content.ContentDetailUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.DeleteFavoriteContentUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.GetFavoriteContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.InsertFavoriteContentUseCase
import com.wodud7308.movieinfo.core.ui.common.BaseFavoriteViewModel
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.util.stateInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val contentDetailUseCase: ContentDetailUseCase,
    getFavoriteContentsUseCase: GetFavoriteContentsUseCase,
    insertFavoriteContentUseCase: InsertFavoriteContentUseCase,
    deleteFavoriteContentUseCase: DeleteFavoriteContentUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseFavoriteViewModel(
    getFavoriteContentsUseCase,
    insertFavoriteContentUseCase,
    deleteFavoriteContentUseCase,
    savedStateHandle
) {
    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    val favoriteState: StateFlow<Boolean>

    init {
        favoriteState = combine(
            _uiState,
            _favoriteContentsFlow
        ) { state, favorites ->
            state to favorites
        }.flatMapLatest { (state, favorites) ->
            flow {
                if(state is DetailUiState.ShowData) {
                    emit(favorites.any { it.id == state.data.content.id })
                } else {
                    emit(false)
                }
            }
        }.stateInViewModel(this, false)
    }

    fun setData(mediaType: MediaType, id: Int) {
        viewModelScope.launch {
            contentDetailUseCase(mediaType, id)
                .onStart { _uiState.value = DetailUiState.Loading }
                .collectLatest { result ->
                    result.onSuccess { data ->
                        _uiState.value = DetailUiState.ShowData(ContentUiModel(data, false))
                    }.onFailure {
                        _uiState.value = DetailUiState.Error
                    }
                }
        }
    }
}
