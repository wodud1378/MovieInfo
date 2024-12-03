package com.wodud7308.movieinfo.core.domain.usecase

import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularContentsUseCase @Inject constructor(
    private val repository: TmdbRepository
) {
    operator fun invoke(contentType: PopularContentType): Flow<Result<List<Content>>> =
        repository.getPopularContents(contentType)
}
