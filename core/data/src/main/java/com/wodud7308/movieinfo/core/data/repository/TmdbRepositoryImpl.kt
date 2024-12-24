package com.wodud7308.movieinfo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wodud7308.movieinfo.core.data.datasource.remote.TmdbDataSource
import com.wodud7308.movieinfo.core.data.model.ContentListApiModel
import com.wodud7308.movieinfo.core.data.model.toDomain
import com.wodud7308.movieinfo.core.data.paging.ContentListPagingSource
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val dataSource: TmdbDataSource,
) : TmdbRepository {

    override fun getContents(mediaType: MediaType, contentType: ContentType): Pager<Int, Content> =
        pagerFromFetch { page ->
            dataSource.getContentList(mediaType, contentType, page = page)
        }

    override fun getSearchResult(mediaType: MediaType, query: String): Pager<Int, Content> =
        pagerFromFetch { page ->
            dataSource.getSearchResult(mediaType, query, page = page)
        }

    private fun pagerFromFetch(fetchContents: suspend (page: Int) -> Result<ContentListApiModel>) =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ContentListPagingSource { page ->
                    fetchContents(page)
                }
            },
        )

    override fun getTrendingContents(contentType: TrendingContentType): Flow<Result<List<Content>>> =
        flow {
            val timeWindow = when (contentType) {
                TrendingContentType.Today -> "day"
                TrendingContentType.ThisWeek -> "week"
                else -> null
            }

            timeWindow?.let { t ->
                emit(dataSource.getTrendingContentList(t).map {
                    it.toDomain()
                })
            }
        }

    override fun getPopularContents(mediaType: MediaType): Flow<Result<List<Content>>> = flow {
        emit(dataSource.getContentList(mediaType, ContentType.Popular).map { it.toDomain() })
    }

    override fun getContentDetail(mediaType: MediaType, id: Int): Flow<Result<Content>> = flow {
        val response = when (mediaType) {
            MediaType.Movie -> dataSource.getMovieDetail(id = id)
            MediaType.Tv -> dataSource.getTvShowDetail(id = id)
            else -> null
        }

        response?.let { r ->
            emit(r.map { it.toDomain(true) })
        }
    }
}
