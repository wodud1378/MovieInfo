package com.wodud7308.movieinfo.core.ui.common

import androidx.paging.CombinedLoadStates

interface PagingLoadStateListener {
    fun onLoadState(loadState: CombinedLoadStates)
}
