package com.wodud7308.movieinfo.core.data.network

import com.wodud7308.movieinfo.core.data.model.ContentListApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    /**** Movie List ****/
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): ContentListApiModel

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): ContentListApiModel

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): ContentListApiModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): ContentListApiModel

    /**** Trending ****/
    @GET("trending/all/{time_window}")
    suspend fun getTrendingContents(
        @Path("time_window")
        timeWindow: String,
        @Query("language")
        language: String = "ko",
    ): ContentListApiModel

    /**** Tv ****/
    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): ContentListApiModel
}
