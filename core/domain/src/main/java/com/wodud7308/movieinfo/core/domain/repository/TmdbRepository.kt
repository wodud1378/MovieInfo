package com.wodud7308.movieinfo.core.domain.repository

import androidx.paging.Pager
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {
    fun getContents(mediaType: MediaType, contentType: ContentType): Pager<Int, Content>

    fun getSearchResult(mediaType: MediaType, query: String): Pager<Int, Content>

    fun getTrendingContents(contentType: TrendingContentType): Flow<Result<List<Content>>>

    fun getPopularContents(mediaType: MediaType): Flow<Result<List<Content>>>

    fun getContentDetail(mediaType: MediaType, id: Int): Flow<Result<Content>>
}
