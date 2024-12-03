package com.wodud7308.movieinfo.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wodud7308.movieinfo.core.domain.common.PopularContentType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.usecase.PopularContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.TrendingContentsUseCase
import com.wodud7308.movieinfo.core.ui.content.ContentListState
import com.wodud7308.movieinfo.feature.home.model.FetchResult
import com.wodud7308.movieinfo.feature.home.model.HomeContentsModel
import com.wodud7308.movieinfo.feature.home.model.HomeContentsType
import com.wodud7308.movieinfo.feature.home.model.HomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingContentsUseCase: TrendingContentsUseCase,
    private val popularContentsUseCase: PopularContentsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = combineContents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    private fun combineContents() = combine(
        // Trending Contents.
        fetch(
            TRENDING_CONTENT_TYPE,
            TrendingContentType.Today,
        ) { type -> trendingContentsUseCase(type) },

        // Popular Contents.
        fetch(
            POPULAR_CONTENT_TYPE,
            PopularContentType.Movie
        ) { type -> popularContentsUseCase(type) }
    ) { trending, popular ->
        contentsStateToUiState(
            trending,
            popular
        )
    }.catch { e ->
        emit(HomeUiState.Error)
    }

    private fun contentsStateToUiState(
        trending: FetchResult,
        popular: FetchResult,
    ) = when {
        trending.state is ContentListState.Loading ||
                popular.state is ContentListState.Loading -> {
            HomeUiState.Loading
        }

        trending.state is ContentListState.Error ||
                popular.state is ContentListState.Error -> {
            HomeUiState.Error
        }

        else -> {
            HomeUiState.Success(toUiModel(trending, popular))
        }
    }

    private fun getContents(fetchResult: FetchResult): List<Content> =
        if (fetchResult.state is ContentListState.Success) {
            fetchResult.state.contents
        } else {
            emptyList()
        }

    private fun toUiModel(
        trending: FetchResult,
        popular: FetchResult
    ): HomeUiModel =
        HomeUiModel(
            listOf(
                HomeContentsModel(
                    HomeContentsType.Trending,
                    trending.tab,
                    getContents(trending)
                ),
                HomeContentsModel(
                    HomeContentsType.Popular,
                    popular.tab,
                    getContents(popular)
                )
            )
        )

    private fun <T : Enum<*>> fetch(
        key: String,
        initialValue: T,
        fetchContent: (T) -> Flow<Result<List<Content>>>,
    ): Flow<FetchResult> =
        fetchTab(key, initialValue).flatMapLatest { type ->
            fetchContent.invoke(type).map { data ->
                FetchResult(type, ContentListState.Success(data.getOrThrow()))
            }.catch { FetchResult(type, ContentListState.Error) }
        }

    fun changeTrendingTab(type: TrendingContentType) {
        savedStateHandle[TRENDING_CONTENT_TYPE] = type
    }

    fun changePopularTab(type: PopularContentType) {
        savedStateHandle[POPULAR_CONTENT_TYPE] = type
    }

    private fun <T> fetchTab(key: String, initialValue: T): StateFlow<T> =
        savedStateHandle.getStateFlow(key, initialValue)
}

private const val TRENDING_CONTENT_TYPE = "trendingContentType"
private const val POPULAR_CONTENT_TYPE = "popularContentType"
