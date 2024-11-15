package com.wodud7308.movieinfo.core.data.network

import com.wodud7308.movieinfo.core.data.model.MovieListApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): MovieListApiModel

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): MovieListApiModel

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): MovieListApiModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): MovieListApiModel
}
