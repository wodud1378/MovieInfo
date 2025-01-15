package com.wodud7308.movieinfo.core.ui.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.wodud7308.movieinfo.core.ui.common.ImageLoadStateListener

fun ImageView.fromUrl(
    url: String,
    loadStateListener: ImageLoadStateListener? = null
) {
    val requestListener = loadStateListener?.let {
        object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                loadStateListener.onLoadState(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                loadStateListener.onLoadState(true)
                return false
            }
        }
    }

    Glide
        .with(context)
        .load(url)
        .listener(requestListener)
        .centerCrop()
        .into(this)
}
