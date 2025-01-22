package com.wodud7308.movieinfo.core.ui.content.holder.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.wodud7308.movieinfo.core.ui.content.holder.BaseContentHolder
import com.wodud7308.movieinfo.core.ui.content.holder.ContentEventListener

interface ContentViewHolderFactory<VB : ViewBinding, VH : BaseContentHolder<VB>> {
    fun create(
        inflater: LayoutInflater,
        parent: ViewGroup,
        contentEventListener: ContentEventListener?
    ) : VH
}
