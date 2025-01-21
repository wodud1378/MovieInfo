package com.wodud7308.movieinfo.core.ui.content.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentSearchBinding

sealed interface ContentBindingWrapper<T> {
    val binding: T
    val poster: ImageView
    val posterError: View
    val title: TextView
    val releaseDate: TextView
    val favoriteIcon: ImageView

    data class Default(
        override val binding: HolderContentBinding,
    ) : ContentBindingWrapper<HolderContentBinding> {
        override val poster: ImageView = binding.poster
        override val posterError: View = binding.posterError
        override val title: TextView = binding.title
        override val releaseDate: TextView = binding.releaseDate
        override val favoriteIcon: ImageView = binding.favorite
    }

    data class Search(
        override val binding: HolderContentSearchBinding,
    ) : ContentBindingWrapper<HolderContentSearchBinding> {
        override val poster: ImageView = binding.poster
        override val posterError: View = binding.posterError
        override val title: TextView = binding.title
        override val releaseDate: TextView = binding.releaseDate
        override val favoriteIcon: ImageView = binding.favorite
    }
}
