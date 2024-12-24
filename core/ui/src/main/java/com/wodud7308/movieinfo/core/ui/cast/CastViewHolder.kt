package com.wodud7308.movieinfo.core.ui.cast

import com.wodud7308.movieinfo.core.domain.model.Cast
import com.wodud7308.movieinfo.core.ui.common.BaseViewHolder
import com.wodud7308.movieinfo.core.ui.common.ImagePath
import com.wodud7308.movieinfo.core.ui.common.ProfileSize
import com.wodud7308.movieinfo.core.ui.databinding.HolderPeopleBinding
import com.wodud7308.movieinfo.core.ui.util.fromUrl

class CastViewHolder(
    binding: HolderPeopleBinding
) : BaseViewHolder<Cast, HolderPeopleBinding>(binding) {
    override fun setData(item: Cast) {
        with(binding) {
            profileImage.fromUrl(ImagePath.urlOf(item.profilePath, ProfileSize.W185))
            name.text = item.name
            character.text = item.character
        }
    }
}
