package com.wodud7308.movieinfo.feature.discover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wodud7308.movieinfo.core.domain.common.ContentType
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.usecase.PagedContentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val pagedContentsUseCase: PagedContentsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val pagerFlow: Flow<PagingData<Content>>
    val mediaTypeFlow: StateFlow<MediaType>
    private val contentTypeFlow: StateFlow<ContentType>

    init {
        mediaTypeFlow = savedStateHandle.getStateFlow(MEDIA_TYPE, MediaType.Movie)
        contentTypeFlow = savedStateHandle.getStateFlow(CONTENT_TYPE, ContentType.NowPlaying)

        pagerFlow = buildDataFlow()
    }

    fun setMediaType(mediaType: MediaType) {
        savedStateHandle[MEDIA_TYPE] = mediaType
    }

    fun setContentType(contentType: ContentType) {
        savedStateHandle[CONTENT_TYPE] = contentType
    }

    private fun buildDataFlow(): Flow<PagingData<Content>> = combine(
        mediaTypeFlow,
        contentTypeFlow,
    ) { mediaType, contentType ->
        mediaType to contentType
    }.flatMapLatest { (mediaType, contentType) ->
        pagedContentsUseCase(mediaType, contentType)
    }.cachedIn(viewModelScope)

    companion object {
        const val MEDIA_TYPE = "mediaType"
        const val CONTENT_TYPE = "contentType"
    }
}
