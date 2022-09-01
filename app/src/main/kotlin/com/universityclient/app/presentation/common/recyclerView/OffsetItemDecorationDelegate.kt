package com.universityclient.app.presentation.common.recyclerView

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OffsetItemDecorationDelegate(
    @Dimension val start: Int = 0,
    @Dimension val middle: Int = 0,
    @Dimension val end: Int = 0
) : ItemDecorationDelegates.ItemDecorationDelegate {

    override fun getItemOffsets(
        index: Int,
        itemCount: Int,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ): Rect {
        val result = Rect()

        if (start > 0 && index == 0)
            result.applyStartOffset(parent, start)

        if (middle > 0 && index < itemCount - 1)
            result.applyEndOffset(parent, middle)

        if (end > 0 && index == parent.childCount - 1)
            result.applyEndOffset(parent, end)

        return result
    }

    private fun Rect.applyStartOffset(parent: RecyclerView, offset: Int) {
        val orientation = (parent.layoutManager as? LinearLayoutManager)?.orientation ?: return

        if (orientation == RecyclerView.VERTICAL)
            top += offset
        else
            left += offset
    }

    private fun Rect.applyEndOffset(parent: RecyclerView, offset: Int) {
        val orientation = (parent.layoutManager as? LinearLayoutManager)?.orientation ?: return

        if (orientation == RecyclerView.VERTICAL)
            bottom += offset
        else
            right += offset
    }
}
