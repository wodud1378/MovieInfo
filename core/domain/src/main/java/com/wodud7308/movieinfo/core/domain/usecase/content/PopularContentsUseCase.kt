package com.wodud7308.movieinfo.core.domain.usecase.content

import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularContentsUseCase @Inject constructor(
    private val repository: TmdbRepository
) {
    operator fun invoke(mediaType: MediaType): Flow<Result<List<Content>>> =
        repository.getPopularContents(mediaType)
}
