package com.wodud7308.movieinfo.core.domain.usecase

import com.wodud7308.movieinfo.core.domain.model.MovieType
import com.wodud7308.movieinfo.core.domain.repository.MovieListRepository
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val repository: MovieListRepository,
) {
    operator fun invoke(movieType: MovieType) = repository.getMovieList(movieType).flow
}
