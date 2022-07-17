package com.universityclient.app.presentation.common.ext

import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

inline fun View.makeOutline(crossinline block: (View, Outline) -> Unit) {
    outlineProvider = object : ViewOutlineProvider() {

        override fun getOutline(view: View, outline: Outline) {
            block(view, outline)
        }
    }
}

fun TextView.setCompoundDrawable(
    left: Drawable? = null,
    top: Drawable? = null,
    right: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawables(left, top, right, bottom)
}

inline fun ViewPager2.onPageChanged(
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {},
    crossinline onPageScrolled: (position: Int, offset: Float, offsetPixels: Int) -> Unit = { _, _, _ -> },
    crossinline onPageSelected: (position: Int) -> Unit = {}
) {
    this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageSelected(position)
        }
    })
}
