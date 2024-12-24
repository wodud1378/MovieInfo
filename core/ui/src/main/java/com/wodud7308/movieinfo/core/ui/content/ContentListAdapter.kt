package com.wodud7308.movieinfo.core.ui.content

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wodud7308.movieinfo.core.domain.model.Content
import com.wodud7308.movieinfo.core.ui.common.BaseListAdapter
import com.wodud7308.movieinfo.core.ui.common.ItemClickListener
import com.wodud7308.movieinfo.core.ui.databinding.HolderContentBinding

class ContentListAdapter(
    itemClickListener: ItemClickListener<Content>? = null
) :
    BaseListAdapter<Content, ContentViewHolder>(
        ContentDiffCallback(),
        itemClickListener
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        HolderContentBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            return ContentViewHolder(this)
        }
    }
}
