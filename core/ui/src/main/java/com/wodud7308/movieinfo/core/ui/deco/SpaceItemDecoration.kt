package com.wodud7308.movieinfo.core.ui.deco

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class SpaceItemDecoration(
    context: Context,
    dp: Int,
    private val spaceApply: SpaceApply
) : RecyclerView.ItemDecoration() {
    private var pixels: Int = 0

    init {
        val density = context.resources.displayMetrics.density
        pixels = (dp * density).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        with(spaceApply) {
            if (left)
                outRect.left = pixels
            if (top)
                outRect.top = pixels
            if (right)
                outRect.right = pixels
            if (bottom)
                outRect.bottom = pixels
        }
    }

    data class SpaceApply(
        val left: Boolean = false,
        val top: Boolean = false,
        val bottom: Boolean = false,
        val right: Boolean = false,
    )
}
