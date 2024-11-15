package com.wodud7308.movieinfo.core.data.di

import com.wodud7308.movieinfo.core.data.repository.MoveListRepositoryImpl
import com.wodud7308.movieinfo.core.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(impl: MoveListRepositoryImpl): MovieListRepository
}
