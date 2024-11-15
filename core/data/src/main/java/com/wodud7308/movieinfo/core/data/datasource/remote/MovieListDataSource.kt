package com.wodud7308.movieinfo.core.data.datasource.remote

import com.wodud7308.movieinfo.core.data.model.MovieListApiModel

interface MovieListDataSource {
    suspend fun getNowPlayingMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<MovieListApiModel>

    suspend fun getUpcomingMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<MovieListApiModel>

    suspend fun getPopularMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<MovieListApiModel>

    suspend fun getTopRatedMovies(
        language: String = "ko",
        page: Int = 1,
    ): Result<MovieListApiModel>
}
