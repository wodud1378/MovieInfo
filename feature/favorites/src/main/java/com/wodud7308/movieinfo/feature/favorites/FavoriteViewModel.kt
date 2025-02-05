package com.wodud7308.movieinfo.feature.favorites

import androidx.lifecycle.SavedStateHandle
import com.wodud7308.movieinfo.core.domain.usecase.favorite.DeleteFavoriteContentUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.GetFavoriteContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.favorite.InsertFavoriteContentUseCase
import com.wodud7308.movieinfo.core.ui.common.BaseFavoriteViewModel
import com.wodud7308.movieinfo.core.ui.content.state.ContentListState
import com.wodud7308.movieinfo.core.ui.util.stateInViewModel
import com.wodud7308.movieinfo.core.ui.util.toContentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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

    private fun buildContentsFlow(): Flow<ContentListState> {
        return _favoriteContentsFlow.map { favorites ->
            ContentListState.Success(favorites.map { it.toContentUiModel() })
        }.onStart {
            ContentListState.Loading
        }.catch {
            ContentListState.Error
        }
    }
}
