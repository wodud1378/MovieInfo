package com.wodud7308.movieinfo.core.ui.content.holder

import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel

interface ContentUiEventListener {
    val onClick: ItemClickListener<ContentUiModel>?
    val onClickFavorite: ItemClickListener<ContentUiModel>?
}
