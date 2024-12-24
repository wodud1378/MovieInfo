package com.wodud7308.movieinfo.core.ui.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.fromUrl(url: String) {
    Glide
        .with(context)
        .load(url)
        .into(this)
}
