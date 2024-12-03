package com.wodud7308.movieinfo.core.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wodud7308.movieinfo.core.data.datasource.remote.TmdbDataSource
import com.wodud7308.movieinfo.core.data.model.toDomain
import com.wodud7308.movieinfo.core.data.paging.MovieListPagingSource
import com.wodud7308.movieinfo.core.domain.common.MovieType
import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val dataSource: TmdbDataSource,
) : TmdbRepository {
    override fun getMovies(movieType: MovieType): Pager<Int, Content> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { MovieListPagingSource(dataSource, movieType) },
    )

    override fun getTrendingContents(contentType: TrendingContentType): Flow<Result<List<Content>>> =
        flow {
            val timeWindow = when (contentType) {
                TrendingContentType.Today -> "day"
                TrendingContentType.ThisWeek -> "week"
                else -> null
            }

            timeWindow?.let { t ->
                emit(dataSource.getTrendingContents(t).map {
                    it.toDomain()
                })
            }
        }

    override fun getPopularContents(contentType: PopularContentType): Flow<Result<List<Content>>> =
        flow {
            val response = when (contentType) {
                PopularContentType.Movie -> dataSource.getPopularMovies()
                PopularContentType.Tv -> dataSource.getPopularTvSeries()
                else -> null
            }

            response?.let { r ->
                emit(r.map { it.toDomain() })
            }
        }
}
