package com.wodud7308.movieinfo.core.ui.deco

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wodud7308.movieinfo.core.ui.util.toPixel

class GridSpacingItemDecoration(
    context: Context,
    dp: Int = 8,
) : RecyclerView.ItemDecoration() {
    private var pixels: Int = 0
    private var halfPixels: Int = 0

    private var spanCount: Int = -1
    private var orientation: Int = -1

    private var applyMethod: ((Rect, Int, Int) -> Unit)? = null

    private var initialized: Boolean = false

    init {
        pixels = dp.toPixel(context)
        halfPixels = pixels / 2
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.setEmpty()

        initialize(parent).also {
            initialized = it

            if (!initialized) {
                return
            }
        }

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION)
            return

        val itemCount = state.itemCount

        applyMethod?.invoke(outRect, position, itemCount)
    }

    private fun initialize(parent: RecyclerView): Boolean {
        if (initialized)
            return true

        when(val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> {
                spanCount = layoutManager.spanCount
                orientation = layoutManager.orientation
            }
            is LinearLayoutManager -> {
                spanCount = 1
                orientation = layoutManager.orientation
            }
        }

        applyMethod = when (orientation) {
            RecyclerView.VERTICAL -> this::applyVertical
            RecyclerView.HORIZONTAL -> this::applyHorizontal
            else -> null
        }

        return applyMethod != null
    }

    private fun applyVertical(
        outRect: Rect,
        position: Int,
        itemCount: Int,
    ) {
        val col = position % spanCount
        val row = position / spanCount
        val lastRowStart =
            itemCount - (itemCount % spanCount).let { if (it == 0) spanCount else it }

        outRect.left = if (col > 0) halfPixels else 0
        outRect.right = if (col < spanCount - 1) halfPixels else 0
        outRect.top = if (row > 0) halfPixels else 0
        outRect.bottom = if (position < lastRowStart) halfPixels else 0
    }

    private fun applyHorizontal(
        outRect: Rect,
        position: Int,
        itemCount: Int,
    ) {
        val row = position % spanCount
        val col = position / spanCount
        val lastColStart =
            itemCount - (itemCount % spanCount).let { if (it == 0) spanCount else it }

        outRect.top = if (row > 0) halfPixels else 0
        outRect.bottom = if (row < spanCount - 1) halfPixels else 0
        outRect.left = if (col > 0) halfPixels else 0
        outRect.right = if (position < lastColStart) halfPixels else 0
    }
}
