package com.wodud7308.movieinfo.core.domain.usecase

import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContentDetailUseCase @Inject constructor(
    private val repository: TmdbRepository
) {
    operator fun invoke(mediaType: MediaType, id: Int): Flow<Result<Content>> =
        repository.getContentDetail(mediaType, id)
}
