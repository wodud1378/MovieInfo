package com.wodud7308.movieinfo.core.ui.content.holder

import android.view.View
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.ui.R
import com.wodud7308.movieinfo.core.ui.common.BaseViewHolder
import com.wodud7308.movieinfo.core.ui.common.ImagePath
import com.wodud7308.movieinfo.core.ui.common.PosterSize
import com.wodud7308.movieinfo.core.ui.model.ContentUiModel
import com.wodud7308.movieinfo.core.ui.util.fromUrl

abstract class BaseContentHolder<VB : ViewBinding>(
    private val contentBinding: ContentBindingWrapper<VB>,
    contentEventListener: ContentUiEventListener?,
) : BaseViewHolder<ContentUiModel, VB>(contentBinding.binding) {
    val data: ContentUiModel? get() = _data

    private var _data: ContentUiModel? = null

    init {
        contentEventListener?.let {
            with(it) {
                onClick?.let { listener ->
                    itemView.setOnClickListener {
                        _data?.let { d ->
                            listener.onClick(d)
                        }
                    }
                }

                if (onClickFavorite != null) {
                    contentBinding.favoriteIcon.setOnClickListener {
                        _data?.let { d ->
                            onClickFavorite!!.onClick(d)
                        }
                    }
                    contentBinding.favoriteIcon.visibility = View.VISIBLE
                } else {
                    contentBinding.favoriteIcon.visibility = View.GONE
                }
            }
        }
    }

    override fun setData(item: ContentUiModel) {
        _data = item
        with(contentBinding) {
            val content = item.content
            loadPoster(content.posterPath, poster, posterError)
            title.text = content.title
            releaseDate.text = content.releaseDate
        }
    }

    fun updateFavoriteIcon(isFavorite: Boolean) {
        contentBinding.favoriteIcon.setImageResource(
            if (isFavorite) {
                R.drawable.ic_favorite_on
            } else {
                R.drawable.ic_favorite_off
            }
        )
    }

    private fun loadPoster(posterPath: String, poster: ImageView, posterError: View) {
        poster.fromUrl(
            ImagePath.urlOf(posterPath, PosterSize.W154),
            PosterLoadStateListener(poster, posterError)
        )
    }
}
