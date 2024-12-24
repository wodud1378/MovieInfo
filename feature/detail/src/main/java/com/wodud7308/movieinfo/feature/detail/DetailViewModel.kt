package com.wodud7308.movieinfo.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wodud7308.movieinfo.core.domain.common.MediaType
import com.wodud7308.movieinfo.core.domain.usecase.ContentDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val contentDetailUseCase: ContentDetailUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun setData(mediaType: MediaType, id: Int) {
        viewModelScope.launch {
            contentDetailUseCase(mediaType, id)
                .onStart { _uiState.value = DetailUiState.Loading }
                .collectLatest { result ->
                    result.onSuccess { data ->
                        _uiState.value = DetailUiState.ShowContent(data)
                    }.onFailure {
                        _uiState.value = DetailUiState.Error
                    }
                }
        }
    }
}
