package com.wodud7308.movieinfo.core.ui.content

import android.view.View
import android.widget.ImageView
import com.wodud7308.movieinfo.core.ui.common.ImageLoadStateListener

class PosterLoadStateListener(
    private val poster: ImageView,
    private val posterError: View
) : ImageLoadStateListener {
    override fun onLoadState(isSuccess: Boolean) {
        if (isSuccess) {
            poster.visibility = View.VISIBLE
            posterError.visibility = View.INVISIBLE
        } else {
            poster.visibility = View.INVISIBLE
            posterError.visibility = View.VISIBLE
        }
    }
}
