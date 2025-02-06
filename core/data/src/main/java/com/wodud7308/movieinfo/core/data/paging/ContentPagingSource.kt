package com.wodud7308.movieinfo.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wodud7308.movieinfo.core.data.model.ContentListApiModel
import com.wodud7308.movieinfo.core.data.model.toDomain
import com.wodud7308.movieinfo.core.domain.model.Content

class ContentPagingSource(
    private val fetchContents: suspend (page: Int) -> Result<ContentListApiModel>
) : PagingSource<Int, Content>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        val page = params.key ?: 1
        val result = fetchContents(page)

        try {
            val data = result.getOrThrow().results.map { it.toDomain() }
            return LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
