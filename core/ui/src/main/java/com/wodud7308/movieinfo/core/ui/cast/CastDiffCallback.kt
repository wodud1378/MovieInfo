package com.wodud7308.movieinfo.core.ui.cast

import androidx.recyclerview.widget.DiffUtil
import com.wodud7308.movieinfo.core.domain.model.Cast


class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
        oldItem == newItem
}
