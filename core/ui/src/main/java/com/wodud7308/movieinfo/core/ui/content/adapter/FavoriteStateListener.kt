package com.wodud7308.movieinfo.core.ui.content.adapter

import com.wodud7308.movieinfo.core.domain.model.FavoriteContent

interface FavoriteStateListener {
    fun onFavoriteStateChange(content: FavoriteContent, isChecked: Boolean)
}
