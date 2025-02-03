package com.wodud7308.movieinfo.core.data.di

import android.content.Context
import androidx.room.Room
import com.wodud7308.movieinfo.core.data.database.FavoriteDao
import com.wodud7308.movieinfo.core.data.database.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): LocalDataBase =
        Room.databaseBuilder(
            context,
            LocalDataBase::class.java,
            LocalDataBase.DB_NAME
        ).build()

    @Singleton
    @Provides
    fun provideFavoriteDao(dataBase: LocalDataBase): FavoriteDao =
        dataBase.getFavoriteDao()
}
