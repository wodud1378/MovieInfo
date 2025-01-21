package com.wodud7308.movieinfo.core.data.datasource.local

import com.wodud7308.movieinfo.core.data.database.FavoriteDao
import com.wodud7308.movieinfo.core.data.database.FavoriteEntity
import com.wodud7308.movieinfo.core.domain.common.MediaType

class LocalDataSource(
    private val favoriteDao: FavoriteDao
) {
    suspend fun getFavoriteList(mediaType: MediaType?): Result<List<FavoriteEntity>> = try {
        val result = if (mediaType == null) {
            favoriteDao.getList()
        } else {
            favoriteDao.getListByMediaType(mediaType.name)
        }

        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun insertFavorite(entity: FavoriteEntity): Result<FavoriteEntity> = try {
        val result = favoriteDao.insert(entity)
        if (result >= 0L) {
            Result.success(entity)
        } else {
            Result.failure(IllegalArgumentException())
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun deleteFavorite(entity: FavoriteEntity): Result<FavoriteEntity> = try {
        val result = favoriteDao.delete(entity)
        if(result > 0) {
            Result.success(entity)
        } else {
            Result.failure(IllegalArgumentException()) }

    } catch (e: Exception) {
        Result.failure(e)
    }
}
