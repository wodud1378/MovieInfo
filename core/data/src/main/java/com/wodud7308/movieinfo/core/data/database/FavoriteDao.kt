package com.wodud7308.movieinfo.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table")
    suspend fun getList(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_table WHERE media_type LIKE :mediaType")
    suspend fun getListByMediaType(mediaType: String): List<FavoriteEntity>

    // 리턴값 = rowId, 에러 발생 시 -1
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteEntity: FavoriteEntity): Long

    // 리턴값 = 제거한 row count, 에러 발생 시 0
    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity): Int
}
