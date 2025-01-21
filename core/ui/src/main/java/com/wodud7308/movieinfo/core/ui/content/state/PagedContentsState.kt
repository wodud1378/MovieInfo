package com.wodud7308.movieinfo.core.ui.content.state

import androidx.paging.PagingData
import com.wodud7308.movieinfo.core.domain.model.Content

sealed interface PagedContentsState {
    data object Loading : PagedContentsState
    data object Error : PagedContentsState
    data class Success(val data: PagingData<Content>) : PagedContentsState
}
