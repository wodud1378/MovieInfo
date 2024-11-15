package com.wodud7308.movieinfo.core.data.datasource.remote

import com.wodud7308.movieinfo.core.data.model.MovieListApiModel
import com.wodud7308.movieinfo.core.data.network.MovieListApi
import javax.inject.Inject

class MovieListDataSourceImpl
@Inject constructor(
    private val api: MovieListApi,
) : MovieListDataSource {
    override suspend fun getNowPlayingMovies(
        language: String,
        page: Int,
    ): Result<MovieListApiModel> = try {
        Result.success(api.getNowPlayingMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getUpcomingMovies(
        language: String,
        page: Int,
    ): Result<MovieListApiModel> = try {
        Result.success(api.getUpcomingMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getPopularMovies(
        language: String,
        page: Int,
    ): Result<MovieListApiModel> = try {
        Result.success(api.getPopularMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getTopRatedMovies(
        language: String,
        page: Int,
    ): Result<MovieListApiModel> = try {
        Result.success(api.getTopRatedMovies(language, page))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
