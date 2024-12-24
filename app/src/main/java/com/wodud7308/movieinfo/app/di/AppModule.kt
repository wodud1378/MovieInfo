package com.wodud7308.movieinfo.app.di

import com.wodud7308.movieinfo.app.splash.NavigationHandlerImpl
import com.wodud7308.movieinfo.core.ui.splash.NavigationHandler
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
