package com.wodud7308.movieinfo.core.domain.usecase

import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagedContentsUseCase @Inject constructor(
    private val repository: TmdbRepository,
) {
    operator fun invoke(mediaType: MediaType, contentType: ContentType): Flow<PagingData<Content>> =
        repository.getContents(mediaType, contentType).flow
}
