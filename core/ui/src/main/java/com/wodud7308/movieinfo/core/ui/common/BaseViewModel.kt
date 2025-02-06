package com.wodud7308.movieinfo.core.ui.common

import androidx.lifecycle.ViewModel
import com.wodud7308.movieinfo.core.ui.model.Message
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {
    protected val _messageFlow: MutableSharedFlow<Message> = MutableSharedFlow()
    val messageFlow = _messageFlow.asSharedFlow()

    protected val _loadingFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val loadingFlow = _loadingFlow.asStateFlow()
}
