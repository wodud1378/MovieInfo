package com.wodud7308.movieinfo.core.data.di

import com.wodud7308.movieinfo.core.data.network.MovieListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideMovieListApi(retrofit: Retrofit): MovieListApi =
        retrofit.create(MovieListApi::class.java)
}
