package com.wodud7308.movieinfo.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wodud7308.movieinfo.core.data.datasource.remote.MovieListDataSource
import com.wodud7308.movieinfo.core.data.model.toDomain
import com.wodud7308.movieinfo.core.domain.model.Movie
import com.wodud7308.movieinfo.core.domain.model.MovieType

class MovieListPagingSource(
    private val dataSource: MovieListDataSource,
    private val movieType: MovieType,
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val result =
            when (movieType) {
                MovieType.NowPlaying -> dataSource.getNowPlayingMovies(page = page)
                MovieType.Upcoming -> dataSource.getUpcomingMovies(page = page)
                MovieType.Popular -> dataSource.getPopularMovies(page = page)
                MovieType.TopRated -> dataSource.getTopRatedMovies(page = page)
            }

        try {
            val data = result.getOrThrow().results.map { it.toDomain() }
            return LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}
