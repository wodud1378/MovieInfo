package com.wodud7308.movieinfo.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}
