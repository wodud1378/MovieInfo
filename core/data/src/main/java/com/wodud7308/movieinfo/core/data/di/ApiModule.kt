package com.wodud7308.movieinfo.core.data.di

import com.wodud7308.movieinfo.core.data.network.TmdbApi
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
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi =
        retrofit.create(TmdbApi::class.java)
}
