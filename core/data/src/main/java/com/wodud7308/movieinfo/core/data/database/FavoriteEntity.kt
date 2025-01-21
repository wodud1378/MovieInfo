package com.wodud7308.movieinfo.core.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "media_type") val mediaType: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
)

fun FavoriteEntity.toDomain(): FavoriteContent =
    FavoriteContent(
        id = id,
        mediaType = MediaType.valueOf(mediaType),
        title = title,
        releaseDate = releaseDate,
        posterPath = posterPath ?: ""
    )

fun FavoriteContent.toEntity(): FavoriteEntity =
    FavoriteEntity(
        id = id,
        mediaType = mediaType.toString(),
        title = title,
        releaseDate = releaseDate,
        posterPath = posterPath.ifEmpty { null }
    )
