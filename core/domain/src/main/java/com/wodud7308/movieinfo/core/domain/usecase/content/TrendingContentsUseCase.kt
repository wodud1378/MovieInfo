package com.wodud7308.movieinfo.core.domain.usecase.content

import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingContentsUseCase @Inject constructor(
    private val repository: TmdbRepository
) {
    operator fun invoke(contentType: TrendingContentType): Flow<Result<List<Content>>> =
        repository.getTrendingContents(contentType)
}
