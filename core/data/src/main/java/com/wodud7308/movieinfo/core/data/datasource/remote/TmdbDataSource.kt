package com.wodud7308.movieinfo.core.data.datasource.remote

import android.util.Log
import com.wodud7308.movieinfo.core.data.model.ContentListApiModel
import com.wodud7308.movieinfo.core.data.model.MovieApiModel
import com.wodud7308.movieinfo.core.data.model.TvShowApiModel
import com.wodud7308.movieinfo.core.data.network.TmdbApi
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import javax.inject.Inject

class TmdbDataSource
@Inject constructor(
    private val api: TmdbApi,
) {
    suspend fun getContentList(
        mediaType: MediaType,
        contentType: ContentType,
        language: String = "ko",
        page: Int = 1,
    ): Result<ContentListApiModel> =
        call {
            api.getContents(
                mediaType.apiPath(),
                contentType.apiPath(),
                language,
                page
            )
        }

    suspend fun getSearchResult(
        mediaType: MediaType,
        query: String,
        language: String = "ko",
        page: Int = 1,
    ): Result<ContentListApiModel> =
        call {
            api.searchContents(
                mediaType.apiPath(),
                query,
                language,
                page
            )
        }

    suspend fun getTrendingContentList(
        timeWindow: String,
        language: String = "ko",
    ): Result<ContentListApiModel> =
        call { api.getTrendingContents(timeWindow, language) }

    suspend fun getMovieDetail(
        language: String = "ko",
        id: Int,
    ): Result<MovieApiModel> =
        call { api.getMovieDetail(id, language) }

    suspend fun getTvShowDetail(
        language: String = "ko",
        id: Int,
    ): Result<TvShowApiModel> =
        call { api.getTvShowDetail(id, language) }

    private suspend fun <T> call(api: suspend () -> T): Result<T> {
        return try {
            Result.success(api())
        } catch (e: Exception) {
            Log.d(TmdbDataSource::class.simpleName, e.toString())
            Result.failure(e)
        }
    }

    private fun MediaType.apiPath() = when (this) {
        MediaType.Movie -> "movie"
        MediaType.Tv -> "tv"
    }

    private fun ContentType.apiPath() = when (this) {
        ContentType.NowPlaying -> "now_playing"
        ContentType.Upcoming -> "upcoming"
        ContentType.Popular -> "popular"
        ContentType.TopRated -> "top_rated"
    }
}
