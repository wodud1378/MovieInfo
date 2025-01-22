package com.wodud7308.movieinfo.core.ui.content.holder

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.content.adapter.FavoriteStateListener

interface ContentEventListener {
    val onClick: ItemClickListener<Content>?
    val onClickFavorite: ItemClickListener<Content>?
    val favoriteState: FavoriteStateListener?
}
