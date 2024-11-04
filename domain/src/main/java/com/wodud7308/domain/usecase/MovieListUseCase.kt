package com.wodud7308.domain.usecase

import com.wodud7308.domain.model.MovieType
import com.wodud7308.domain.repository.MovieListRepository

class MovieListUseCase(
    private val repository: MovieListRepository,
) {
    operator fun invoke(movieType: MovieType) = repository.getMovieList(movieType)
}
