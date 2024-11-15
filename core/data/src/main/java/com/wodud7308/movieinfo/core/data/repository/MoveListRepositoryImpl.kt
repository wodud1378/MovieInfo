package com.wodud7308.movieinfo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wodud7308.movieinfo.core.data.datasource.remote.MovieListDataSource
import com.wodud7308.movieinfo.core.data.paging.MovieListPagingSource
import com.wodud7308.movieinfo.core.domain.model.Movie
import com.wodud7308.movieinfo.core.domain.model.MovieType
import com.wodud7308.movieinfo.core.domain.repository.MovieListRepository
import javax.inject.Inject

class MoveListRepositoryImpl @Inject constructor(
    private val dataSource: MovieListDataSource,
) : MovieListRepository {
    override fun getMovieList(movieType: MovieType): Pager<Int, Movie> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { MovieListPagingSource(dataSource, movieType) },
    )
}
