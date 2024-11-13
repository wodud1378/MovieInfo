package com.wodud7308.movieinfo.di

import com.wodud7308.movieinfo.splash.NavigationHandlerImpl
import com.wodud7308.presentation.splash.NavigationHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindNavigationHandler(navigationHandlerImpl: NavigationHandlerImpl): NavigationHandler
}
