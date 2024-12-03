package com.wodud7308.movieinfo.core.domain.repository

import androidx.paging.Pager
import com.wodud7308.movieinfo.core.domain.common.MovieType
import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {
    fun getMovies(movieType: MovieType): Pager<Int, Content>

    fun getTrendingContents(contentType: TrendingContentType): Flow<Result<List<Content>>>

    fun getPopularContents(contentType: PopularContentType): Flow<Result<List<Content>>>
}
