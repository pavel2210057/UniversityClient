package com.universityclient.app.presentation.common.recyclerView

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import java.security.InvalidParameterException

class ItemDecorationDelegates (
    private val delegatesMap: Map<Class<*>, List<ItemDecorationDelegate>>
) : RecyclerView.ItemDecoration() {

    private val delegateMapKeys = delegatesMap.keys

    private var cache: Pair<Class<*>, List<ItemDecorationDelegate>>? = null

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        applyForEach(parent) { delegate, childView ->
            delegate.onDraw(canvas, childView, parent, state)
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        applyForEach(parent) { delegate, childView ->
            delegate.onDrawOver(canvas, childView, parent, state)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = (parent.adapter as? AsyncListDifferDelegationAdapter<*>)
            ?: throw InvalidParameterException("Adapter must be of the ListDelegationAdapter type")

        val index = parent.indexOfChild(view)
        val item = adapter.items[index]

        val cache = this.cache
        if (cache != null && cache.first.isInstance(item)) {
            cache.second.calculateOffset(index, adapter.itemCount, view, parent, state, outRect)
            return
        }

        val keys = delegateMapKeys.filter { clazz ->
            clazz.isInstance(item)
        }
        val delegates = mutableListOf<ItemDecorationDelegate>()

        for (key in keys) {
            val itemDecorationDelegate = delegatesMap.getValue(key)
            delegates += itemDecorationDelegate

            itemDecorationDelegate
                .calculateOffset(index, adapter.itemCount, view, parent, state, outRect)
        }

        if (keys.isNotEmpty())
            this.cache = keys[0] to delegates
    }

    private fun List<ItemDecorationDelegate>.calculateOffset(
        index: Int,
        itemCount: Int,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
        outRect: Rect
    ) = forEach {
        val rect = it.getItemOffsets(index, itemCount, view, parent, state)

        outRect.left += rect.left
        outRect.top += rect.top
        outRect.right += rect.right
        outRect.bottom += rect.bottom
    }

    private fun applyForEach(
        parent: RecyclerView,
        block: (delegate: ItemDecorationDelegate, childView: View) -> Unit
    ) {
        val adapter = (parent.adapter as? AsyncListDifferDelegationAdapter<*>)
            ?: throw InvalidParameterException("Adapter must be of the ListDelegationAdapter type")

        for ((index, item) in adapter.items.withIndex()) {
            val view = parent.getChildAt(index)

            val cache = this.cache
            if (cache != null && cache.first.isInstance(item)) {
                cache.second.applyBlockForEachDelegate(view, block)
                continue
            }

            val keys = delegateMapKeys.filter { clazz ->
                clazz.isInstance(item)
            }

            val delegates = mutableListOf<ItemDecorationDelegate>()

            for (key in keys) {
                val itemDecorationDelegates = delegatesMap.getValue(key)
                delegates += itemDecorationDelegates

                itemDecorationDelegates.applyBlockForEachDelegate(view, block)
            }

            if (keys.isNotEmpty())
                this.cache = keys[0] to delegates
        }
    }

    private fun List<ItemDecorationDelegate>.applyBlockForEachDelegate(
        view: View,
        block: (delegate: ItemDecorationDelegate, childView: View) -> Unit
    ) = forEach { delegate -> block(delegate, view) }

    class Builder<T> {

        private val delegatesMap = HashMap<Class<*>, MutableList<ItemDecorationDelegate>>()

        fun <I> addItemDecoration(
            clazz: Class<I>,
            vararg itemDecorationDelegates: ItemDecorationDelegate
        ): Builder<T> = apply {
            delegatesMap[clazz]?.addAll(itemDecorationDelegates)
                ?: run { delegatesMap[clazz] = itemDecorationDelegates.toMutableList() }
        }

        fun build() = ItemDecorationDelegates(
            delegatesMap.mapValues { it.value.toList() }
        )
    }

    interface ItemDecorationDelegate {

        /**
         * Callback that is called on child with specified
         * AdapterDelegate type when it should be drawn
         * before the main view
         *
         * @param canvas canvas to draw into
         * @param view   view provided by AdapterDelegate
         * @param parent parent
         * @param state  current parent's state
         */
        fun onDraw(
            canvas: Canvas,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {}

        /**
         * Callback that is called on child with specified
         * AdapterDelegate type when it should be drawn
         * after the main view
         *
         * @param canvas canvas to draw into
         * @param view   view provided by AdapterDelegate
         * @param parent parent
         * @param state  current parent's state
         */
        fun onDrawOver(
            canvas: Canvas,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {}

        /**
         * Callback that is called on child with specified
         * AdapterDelegate type to make offset for the view
         *
         * @param view   view provided by AdapterDelegate
         * @param parent parent
         * @param state  current parent's state
         *
         * @return output rect
         */
        fun getItemOffsets(
            index: Int,
            itemCount: Int,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ): Rect = Rect()
    }
}

inline fun<reified I : T, T> ItemDecorationDelegates.Builder<T>.addItemDecoration(
    vararg itemDecorationDelegates: ItemDecorationDelegates.ItemDecorationDelegate
) = addItemDecoration(I::class.java, *itemDecorationDelegates)
