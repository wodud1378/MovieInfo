package com.wodud7308.movieinfo.core.data.datasource.remote

import com.wodud7308.movieinfo.core.data.model.ContentListApiModel
import com.wodud7308.movieinfo.core.data.network.TmdbApi
import javax.inject.Inject

class TmdbDataSource
@Inject constructor(
    private val api: TmdbApi,
) {
    suspend fun getNowPlayingMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<ContentListApiModel> = try {
        Result.success(api.getNowPlayingMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getUpcomingMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<ContentListApiModel> = try {
        Result.success(api.getUpcomingMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getPopularMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<ContentListApiModel> = try {
        Result.success(api.getPopularMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getTopRatedMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<ContentListApiModel> = try {
        Result.success(api.getTopRatedMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getTrendingContents(
        timeWindow: String,
        language: String = "ko",
    ): Result<ContentListApiModel> = try {
        Result.success(api.getTrendingContents(timeWindow, language))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getPopularTvSeries(
        language: String = "ko",
        page: Int = 1,
    ): Result<ContentListApiModel> = try {
        Result.success(api.getPopularTvSeries(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
