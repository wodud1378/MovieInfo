package com.wodud7308.movieinfo.core.domain.usecase.favorite

import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.FavoriteContent
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteContentsUseCase @Inject constructor(
    private val repository: TmdbRepository
) {
    operator fun invoke(mediaType: MediaType?): Flow<Result<List<FavoriteContent>>> =
        repository.getFavoriteList(mediaType)
}
