package com.wodud7308.movieinfo.core.ui.content

import android.view.View
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.BaseViewHolder
import com.wodud7308.movieinfo.core.ui.common.ImageLoadStateListener
import com.wodud7308.movieinfo.core.ui.common.ImagePath
import com.wodud7308.movieinfo.core.ui.common.PosterSize
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding
import com.wodud7308.movieinfo.core.ui.util.fromUrl

class ContentViewHolder(
    binding: HolderContentBinding
) : BaseViewHolder<Content, HolderContentBinding>(binding) {
    override fun setData(item: Content) {
        with(binding) {
            poster.fromUrl(ImagePath.urlOf(item.posterPath, PosterSize.W154), imageLoadListener)
            title.text = item.title
            releaseDate.text = item.releaseDate
        }
    }

    private val imageLoadListener = object : ImageLoadStateListener {
        override fun onLoadState(isSuccess: Boolean) {
            with(binding) {
                if (isSuccess) {
                    poster.visibility = View.VISIBLE
                    posterError.visibility = View.INVISIBLE
                } else {
                    poster.visibility = View.INVISIBLE
                    posterError.visibility = View.VISIBLE
                }
            }
        }
    }
}
