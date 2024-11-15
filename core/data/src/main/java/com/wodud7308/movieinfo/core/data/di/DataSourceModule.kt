package com.wodud7308.movieinfo.core.data.di

import com.wodud7308.movieinfo.core.data.datasource.remote.MovieListDataSource
import com.wodud7308.movieinfo.core.data.datasource.remote.MovieListDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun provideMoveListDataSource(impl: MovieListDataSourceImpl): MovieListDataSource
}
