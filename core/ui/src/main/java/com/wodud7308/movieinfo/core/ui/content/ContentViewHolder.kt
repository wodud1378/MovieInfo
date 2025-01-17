package com.wodud7308.movieinfo.core.ui.content

import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class ContentViewHolder(
    binding: HolderContentBinding,
) : BaseContentHolder<HolderContentBinding>(binding) {
    override fun setData(item: Content) {
        with(binding) {
            loadPoster(item.posterPath, poster, posterError)
            title.text = item.title
            releaseDate.text = item.releaseDate
        }
    }
}
