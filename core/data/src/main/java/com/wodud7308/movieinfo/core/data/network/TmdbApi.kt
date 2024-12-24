package com.wodud7308.movieinfo.core.data.network

import com.wodud7308.movieinfo.core.data.model.ContentListApiModel
import com.wodud7308.movieinfo.core.data.model.MovieApiModel
import com.wodud7308.movieinfo.core.data.model.TvShowApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("{media_type}/{content_type}")
    suspend fun getContents(
        @Path("media_type")
        mediaType: String,
        @Path("content_type")
        contentType: String,
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): ContentListApiModel

    @GET("trending/all/{time_window}")
    suspend fun getTrendingContents(
        @Path("time_window")
        timeWindow: String,
        @Query("language")
        language: String = "ko",
    ): ContentListApiModel

    @GET("search/{media_type}")
    suspend fun searchContents(
        @Path("media_type")
        mediaType: String,
        @Query("query")
        query: String,
        @Query("language")
        language: String = "ko",
        @Query("page")
        page: Int = 1,
    ): ContentListApiModel

    @GET("movie/{movie_id}?append_to_response=credits")
    suspend fun getMovieDetail(
        @Path("movie_id")
        movieId: Int,
        @Query("language")
        language: String = "ko",
    ): MovieApiModel

    @GET("tv/{series_id}?append_to_response=credits")
    suspend fun getTvShowDetail(
        @Path("series_id")
        seriesId: Int,
        @Query("language")
        language: String = "ko",
    ): TvShowApiModel
}
