package com.wodud7308.movieinfo.core.ui.content.holder

import android.view.View
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.R
import com.wodud7308.movieinfo.core.ui.common.BaseViewHolder
import com.wodud7308.movieinfo.core.ui.common.ImagePath
import com.wodud7308.movieinfo.core.ui.common.PosterSize
import com.wodud7308.movieinfo.core.ui.util.fromUrl

abstract class BaseContentHolder<VB : ViewBinding>(
    private val contentBinding: ContentBindingWrapper<VB>,
    contentEventListener: ContentEventListener?,
) : BaseViewHolder<Content, VB>(contentBinding.binding) {
    private var data: Content? = null

    init {
        contentEventListener?.let {
            with(it) {
                onClick?.let { listener ->
                    itemView.setOnClickListener {
                        data?.let {
                            listener.onClick(it)
                        }
                    }
                }

                onClickFavorite?.let { listener ->
                    contentBinding.favoriteIcon.setOnClickListener {
                        data?.let {
                            listener.onClick(it)
                        }
                    }
                }
            }
        }
    }

    override fun setData(item: Content) {
        data = item

        with(contentBinding) {
            loadPoster(item.posterPath, poster, posterError)
            title.text = item.title
            releaseDate.text = item.releaseDate
        }
    }

    private fun loadPoster(posterPath: String, poster: ImageView, posterError: View) {
        poster.fromUrl(
            ImagePath.urlOf(posterPath, PosterSize.W154),
            PosterLoadStateListener(poster, posterError)
        )
    }

    protected fun updateFavoriteImage(imageView: ImageView, isChecked: Boolean) {
        imageView.setImageResource(
            if (isChecked) {
                R.drawable.ic_favorite_on
            } else {
                R.drawable.ic_favorite_off
            }
        )
    }
}
