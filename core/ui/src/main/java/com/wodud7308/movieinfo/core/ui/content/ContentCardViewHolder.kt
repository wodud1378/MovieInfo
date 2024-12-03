package com.wodud7308.movieinfo.core.ui.content

import com.bumptech.glide.Glide
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.BaseViewHolder
import com.wodud7308.movieinfo.core.ui.common.PosterPath
import com.wodud7308.movieinfo.core.ui.common.PosterSize
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class ContentCardViewHolder(
    binding: HolderContentBinding
) : BaseViewHolder<Content, HolderContentBinding>(binding) {
    override fun setData(item: Content) {
        with(binding) {
            with(poster) {
                Glide
                    .with(context)
                    .load(PosterPath.urlOf(item.posterPath, PosterSize.W154))
                    .into(this)
            }

            title.text = item.title
            releaseDate.text = item.releaseDate
        }
    }
}
