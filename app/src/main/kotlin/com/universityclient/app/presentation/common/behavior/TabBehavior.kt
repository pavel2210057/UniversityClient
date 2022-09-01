package com.universityclient.app.presentation.common.behavior

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.universityclient.app.presentation.common.ext.onPageChanged

typealias TabChangedCallback<T> = (view: T, position: Int) -> Unit

/**
 * Note: order of views, being sent in constructor, is important,
 *       i.e. the first view will be connected to the first ViewPager
 *       position and so on.
 * */
class TabBehavior<T : View>(
    private vararg val tabViews: T
) {

    private val tabChangedListeners: MutableList<TabChangedCallback<T>> = mutableListOf()

    init {
        tabViews.forEachIndexed { index, tabView ->
            tabView.setOnClickListener { selectTab(index) }
        }
    }

    fun bindViewPager(viewPager: ViewPager2, needSmoothScroll: Boolean = false) {
        selectTab(viewPager.currentItem)
        viewPager.onPageChanged(
            onPageSelected = ::selectTab
        )
        addTabChangedCallback { _, position ->
            viewPager.setCurrentItem(position, needSmoothScroll)
        }
    }

    fun addTabChangedCallback(callback: TabChangedCallback<T>) {
        tabChangedListeners += callback
    }

    fun removeTabChangedCallback(callback: TabChangedCallback<T>) {
        tabChangedListeners -= callback
    }

    fun clearCallbacks() {
        tabChangedListeners.clear()
    }

    fun selectTab(position: Int) {
        if (position > tabViews.lastIndex)
            error("ViewPager position must be within the tab count")


        val tabView = tabViews[position]
        if (tabView.isSelected)
            return

        resetViewStates()
        tabView.isSelected = true

        tabChangedListeners.forEach { callback -> callback(tabView, position) }
    }

    private fun resetViewStates() {
        tabViews.forEach { it.isSelected = false }
    }
}
