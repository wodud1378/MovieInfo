package com.wodud7308.movieinfo.core.data.di

import com.wodud7308.movieinfo.core.data.datasource.remote.TmdbDataSource
import com.wodud7308.movieinfo.core.data.network.TmdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideMoveListDataSource(api: TmdbApi): TmdbDataSource = TmdbDataSource(api)
}
