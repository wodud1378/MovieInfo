package com.wodud7308.movieinfo.core.ui.model

import com.wodud7308.movieinfo.core.ui.common.MessageType

data class Message(
    val type: MessageType,
    val extraText: String? = null
)
