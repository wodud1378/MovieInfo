package com.wodud7308.movieinfo.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.model.Movie
import com.wodud7308.movieinfo.core.domain.model.MovieType
import com.wodud7308.movieinfo.core.domain.usecase.MovieListUseCase
import com.wodud7308.movieinfo.core.presentation.util.withViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
) : ViewModel() {
    init {
        selectType(MovieType.NowPlaying)
    }

    private val existType: MutableLiveData<MovieType?> = MutableLiveData(null)
    private val _movieList = MutableStateFlow(PagingData.empty<Movie>())
    val movieList: StateFlow<PagingData<Movie>> get() = _movieList

    fun selectType(movieType: MovieType) {
        if (existType.value == movieType) {
            return
        }

        existType.value = movieType
        movieListUseCase(movieType).withViewModel(this)
    }
}
