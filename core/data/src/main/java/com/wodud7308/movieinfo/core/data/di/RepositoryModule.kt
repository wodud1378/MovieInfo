package com.wodud7308.movieinfo.core.data.di

import com.wodud7308.movieinfo.core.data.repository.TmdbRepositoryImpl
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
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
    abstract fun bindMovieListRepository(impl: TmdbRepositoryImpl): TmdbRepository
}
