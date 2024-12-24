package com.wodud7308.movieinfo.core.ui.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wodud7308.movieinfo.core.domain.model.Cast
import com.wodud7308.movieinfo.core.ui.common.BaseListAdapter
import com.wodud7308.movieinfo.core.ui.databinding.HolderPeopleBinding

class CastListAdapter : BaseListAdapter<Cast, CastViewHolder>(CastDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
         HolderPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
             return CastViewHolder(this)
         }
    }
}
