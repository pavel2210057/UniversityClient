package com.universityclient.app.presentation.common.ext

import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

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

typealias OnTransitionChange = (
    motionLayout: MotionLayout,
    startId: Int,
    endId: Int,
    progress: Float
) -> Unit

typealias OnTransitionStarted = (
    motionLayout: MotionLayout,
    startId: Int,
    endId: Int
) -> Unit

typealias OnTransitionTrigger = (
    motionLayout: MotionLayout,
    triggerId: Int,
    positive: Boolean,
    progress: Float
) -> Unit

typealias OnTransitionCompleted = (
    motionLayout: MotionLayout,
    currentId: Int
) -> Unit

inline fun MotionLayout.addTransitionListener(
    crossinline onTransitionChange: OnTransitionChange = { _, _, _, _ -> },
    crossinline onTransitionStarted: OnTransitionStarted = { _, _, _ -> },
    crossinline onTransitionTrigger: OnTransitionTrigger = { _, _, _, _ -> },
    crossinline onTransitionCompleted: OnTransitionCompleted = { _, _ -> }
) {
    addTransitionListener(object : MotionLayout.TransitionListener {

        override fun onTransitionChange(
            motionLayout: MotionLayout,
            startId: Int,
            endId: Int,
            progress: Float
        ) {
            onTransitionChange(motionLayout, startId, endId, progress)
        }

        override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {
            onTransitionStarted(motionLayout, startId, endId)
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {
            onTransitionTrigger(motionLayout, triggerId, positive, progress)
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
            onTransitionCompleted(motionLayout, currentId)
        }
    })
}

inline fun RequestBuilder<Drawable>.onResourceReady(
    preventDefault: Boolean = true,
    crossinline block: (Drawable) -> Unit
) =
    addListener(object : RequestListener<Drawable> {

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>,
            dataSource: DataSource,
            isFirstResource: Boolean
        ): Boolean {
            block(resource)
            return preventDefault
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any,
            target: Target<Drawable>,
            isFirstResource: Boolean
        ): Boolean = false
    })
