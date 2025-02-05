package com.wodud7308.movieinfo.core.ui.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {
    protected val _messageFlow = MutableSharedFlow<String>()
    val messageFlow = _messageFlow.asSharedFlow()

    protected val _loadingFlow = MutableStateFlow(0)
    val loadingFlow = _loadingFlow.asStateFlow()
}
