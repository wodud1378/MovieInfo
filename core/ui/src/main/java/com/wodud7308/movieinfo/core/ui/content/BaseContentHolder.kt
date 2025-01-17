package com.wodud7308.movieinfo.core.ui.content

import android.view.View
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.BaseViewHolder
import com.wodud7308.movieinfo.core.ui.common.ImagePath
import com.wodud7308.movieinfo.core.ui.common.PosterSize
import com.wodud7308.movieinfo.core.ui.util.fromUrl

abstract class BaseContentHolder<VB : ViewBinding>(
    binding: VB
) : BaseViewHolder<Content, VB>(binding) {

    protected fun loadPoster(posterPath: String, poster: ImageView, posterError : View) {
        poster.fromUrl(
            ImagePath.urlOf(posterPath, PosterSize.W154),
            PosterLoadStateListener(poster, posterError)
        )
    }
}
