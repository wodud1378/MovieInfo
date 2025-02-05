package com.wodud7308.movieinfo.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.common.TrendingContentType
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.domain.usecase.content.PopularContentsUseCase
import com.wodud7308.movieinfo.core.domain.usecase.content.TrendingContentsUseCase
import com.wodud7308.movieinfo.core.ui.common.BaseViewModel
import com.wodud7308.movieinfo.core.ui.content.state.ContentListState
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.util.stateInViewModel
import com.wodud7308.movieinfo.feature.home.model.FetchContent
import com.wodud7308.movieinfo.feature.home.model.HomeContentsModel
import com.wodud7308.movieinfo.feature.home.model.HomeContentsType
import com.wodud7308.movieinfo.feature.home.model.HomeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingContentsUseCase: TrendingContentsUseCase,
    private val popularContentsUseCase: PopularContentsUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val uiState: StateFlow<HomeUiState> = buildUiFlow()
        .stateInViewModel(this, HomeUiState.Loading)

    private fun buildUiFlow() = combine(
        // Trending Contents.
        fetch(
            TRENDING_CONTENT_TYPE,
            TrendingContentType.Today,
        ) { type -> trendingContentsUseCase(type) },

        // Popular Contents.
        fetch(
            POPULAR_CONTENT_TYPE,
            MediaType.Movie
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
        trending: FetchContent,
        popular: FetchContent,
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
            HomeUiState.ShowData(toUiModel(trending, popular))
        }
    }

    private fun getContents(fetchResult: FetchContent): List<ContentUiModel> =
        if (fetchResult.state is ContentListState.Success) {
            fetchResult.state.contents
        } else {
            emptyList()
        }

    private fun toUiModel(
        trending: FetchContent,
        popular: FetchContent
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
        fetchLogic: (T) -> Flow<Result<List<Content>>>,
    ): Flow<FetchContent> =
        savedStateHandle.getStateFlow(key, initialValue).flatMapLatest { type ->
            fetchLogic(type).map { data ->
                FetchContent(
                    type,
                    ContentListState.Success(data.getOrThrow().map { ContentUiModel(it, false) })
                )
            }.onStart {
                FetchContent(type, ContentListState.Loading)
            }.catch {
                FetchContent(type, ContentListState.Error)
            }
        }

    fun setTrendingContentType(type: TrendingContentType) {
        savedStateHandle[TRENDING_CONTENT_TYPE] = type
    }

    fun setPopularContentType(type: MediaType) {
        savedStateHandle[POPULAR_CONTENT_TYPE] = type
    }

    companion object {
        private const val TRENDING_CONTENT_TYPE = "trendingContentType"
        private const val POPULAR_CONTENT_TYPE = "popularContentType"
    }
}
