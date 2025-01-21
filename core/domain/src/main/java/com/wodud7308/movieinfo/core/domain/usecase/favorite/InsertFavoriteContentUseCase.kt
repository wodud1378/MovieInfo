package com.wodud7308.movieinfo.core.domain.usecase.favorite

import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFavoriteContentUseCase @Inject constructor(
    private val repository: TmdbRepository
) {
    operator fun invoke(favoriteContent: FavoriteContent): Flow<Result<FavoriteContent>> =
        repository.insertFavorite(favoriteContent)
}
