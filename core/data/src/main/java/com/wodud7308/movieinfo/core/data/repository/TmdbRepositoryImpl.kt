package com.wodud7308.movieinfo.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.wodud7308.movieinfo.core.data.database.toDomain
import com.wodud7308.movieinfo.core.data.database.toEntity
import com.wodud7308.movieinfo.core.data.datasource.local.LocalDataSource
import com.wodud7308.movieinfo.core.data.datasource.remote.TmdbDataSource
import com.wodud7308.movieinfo.core.data.model.ContentListApiModel
import com.wodud7308.movieinfo.core.data.model.toDomain
import com.wodud7308.movieinfo.core.data.paging.ContentListPagingSource
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val tmdbDataSource: TmdbDataSource,
) : TmdbRepository {

    override fun getContents(mediaType: MediaType, contentType: ContentType): Pager<Int, Content> =
        pagerFromFetch { page ->
            tmdbDataSource.getContentList(mediaType, contentType, page = page)
        }

    override fun getSearchResult(mediaType: MediaType, query: String): Pager<Int, Content> =
        pagerFromFetch { page ->
            tmdbDataSource.getSearchResult(mediaType, query, page = page)
        }

    private fun pagerFromFetch(fetchContents: suspend (page: Int) -> Result<ContentListApiModel>) =
        Pager(
            // Tmdb api 페이지 처리는 20개로 고정 되어 변경 불가능.
            config = PagingConfig(20),
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
                emit(tmdbDataSource.getTrendingContentList(t).map {
                    it.toDomain()
                })
            }
        }

    override fun getPopularContents(mediaType: MediaType): Flow<Result<List<Content>>> =
        flow {
            emit(
                tmdbDataSource.getContentList(mediaType, ContentType.Popular).map { it.toDomain() })
        }

    override fun getContentDetail(mediaType: MediaType, id: Int): Flow<Result<Content>> =
        flow {
            val response = when (mediaType) {
                MediaType.Movie -> tmdbDataSource.getMovieDetail(id = id)
                MediaType.Tv -> tmdbDataSource.getTvShowDetail(id = id)
                else -> null
            }

            response?.let { r ->
                emit(r.map { it.toDomain(true) })
            }
        }

    override fun getFavoriteList(mediaType: MediaType?): Flow<Result<List<FavoriteContent>>> =
        flow {
            emit(localDataSource.getFavoriteList(mediaType).map { it.map { e -> e.toDomain() } })
        }

    override fun insertFavorite(favoriteContent: FavoriteContent): Flow<Result<FavoriteContent>> =
        flow {
            emit(localDataSource.insertFavorite(favoriteContent.toEntity()).map { it.toDomain() })
        }

    override fun deleteFavorite(favoriteContent: FavoriteContent): Flow<Result<FavoriteContent>> =
        flow {
            emit(localDataSource.deleteFavorite(favoriteContent.toEntity()).map { it.toDomain() })
        }
}
