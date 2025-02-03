package com.wodud7308.movieinfo.core.ui.model

import androidx.paging.PagingData
import androidx.paging.map
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

data class ContentUiModel(
    val content: Content,
    val isFavorite: Boolean
)

@JvmName("fromPagingData")
fun toUiModelFlow(
    contentsFlow: Flow<PagingData<Content>>,
    favoritesFlow: Flow<List<FavoriteContent>>
): Flow<PagingData<ContentUiModel>> {
    return toUiModelFlow(
        contentsFlow,
        favoritesFlow
    ) { contents, favorites ->
        contents.map { content ->
            ContentUiModel(
                content,
                favorites.any { it.id == content.id }
            )
        }
    }
}

@JvmName("fromList")
fun toUiModelFlow(
    contentsFlow: Flow<List<Content>>,
    favoritesFlow: Flow<List<FavoriteContent>>
): Flow<List<ContentUiModel>> {
    return toUiModelFlow(
        contentsFlow,
        favoritesFlow
    ) { contents, favorites ->
        contents.map { content ->
            ContentUiModel(
                content,
                favorites.any { it.id == content.id }
            )
        }
    }
}

fun <T, R> toUiModelFlow(
    contentsFlow: Flow<T>,
    favoritesFlow: Flow<List<FavoriteContent>>,
    mapContents: (T, List<FavoriteContent>) -> R
): Flow<R> {
    return combine(
        contentsFlow,
        favoritesFlow,
    ) { contents, favorites ->
        mapContents(contents, favorites)
    }
}
