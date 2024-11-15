package com.wodud7308.movieinfo.core.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.withViewModel(
    viewModel: ViewModel,
    initialValue: T,
    stopMillis: Long = 5000L,
): StateFlow<T> =
    this.stateIn(
        viewModel.viewModelScope,
        SharingStarted.WhileSubscribed(stopMillis),
        initialValue,
    )

fun <T : Any> Flow<PagingData<T>>.withViewModel(
    viewModel: ViewModel,
    initialValue: PagingData<T> = PagingData.empty(),
    stopMillis: Long = 5000L,
): StateFlow<PagingData<T>> =
    this
        .cachedIn(viewModel.viewModelScope)
        .stateIn(
            viewModel.viewModelScope,
            SharingStarted.WhileSubscribed(stopMillis),
            initialValue,
        )
