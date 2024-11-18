package com.wodud7308.movieinfo.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.model.Movie
import com.wodud7308.movieinfo.core.domain.model.MovieType
import com.wodud7308.movieinfo.core.domain.usecase.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val movieType = savedStateHandle.getStateFlow(MOVIE_TYPE, MovieType.NowPlaying)
    val uiState: StateFlow<HomeUiState> =
        movieType.flatMapLatest { type ->
            movieListUseCase(type)
                .map<PagingData<Movie>, HomeUiState> { data ->
                    HomeUiState.Success(movies = data)
                }
                .catch { emit(HomeUiState.Error) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    fun selectMovieListType(type: MovieType) {
        savedStateHandle[MOVIE_TYPE] = type
    }
}

private const val MOVIE_TYPE = "movieType"
