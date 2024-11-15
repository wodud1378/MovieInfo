package com.wodud7308.movieinfo.core.domain.repository

import androidx.paging.Pager
import com.wodud7308.movieinfo.core.domain.model.Movie
import com.wodud7308.movieinfo.core.domain.model.MovieType

interface MovieListRepository {
    fun getMovieList(movieType: MovieType): Pager<Int, Movie>
}
