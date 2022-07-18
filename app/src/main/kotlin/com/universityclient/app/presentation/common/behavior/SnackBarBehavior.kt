package com.universityclient.app.presentation.common.behavior

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.*
import androidx.viewbinding.ViewBinding
import com.google.android.material.behavior.SwipeDismissBehavior
import kotlinx.coroutines.Runnable

class SnackBarBehavior<B : ViewBinding>(
    private val snackBarBinding: B,
    private val duration: Long = DEFAULT_DURATION,
    private val topDelay: Long = DEFAULT_TOP_DELAY
) {

    private val snackBarView: View
        get() = snackBarBinding.root

    private var hideAfterDelayRunnable: Runnable? = null
    private var sh = StateHolder(State.Hidden)

    private val hiddenPosition: Float
        get() = -snackBarView.height.toFloat()

    private val shownPosition: Float
        get() = 0f

    fun setup() {
        snackBarView.doOnLayout {
            resetSnackBar()
            setupSwipeDismissBehavior()
        }
    }

    fun show(action: (B) -> Unit) {
        if (!snackBarView.isLaidOut) {
            snackBarView.doOnLayout { showInternal(action) }
            return
        }

        when (sh.state) {
            State.Shown -> closeAndShowInternal(action)
            State.Hidden -> showInternal(action)
            State.Settling -> waitForSettling { show(action) }
        }
    }

    fun hide() {
        when (sh.state) {
            State.Shown -> hideInternal()
            State.Hidden -> return
            State.Settling -> waitForSettling { hide() }
        }
    }

    private fun setupSwipeDismissBehavior() {
        snackBarView.updateLayoutParams<CoordinatorLayout.LayoutParams> {
            behavior = SwipeDismissBehavior<View>().apply {
                listener = object : SwipeDismissBehavior.OnDismissListener {

                    override fun onDragStateChanged(state: Int) {}

                    override fun onDismiss(view: View) {
                        snackBarView.apply {
                            alpha = 1f
                            resetSnackBar()
                            requestLayout()
                        }
                    }
                }
            }
        }
    }

    private fun showInternal(action: (B) -> Unit) {
        action(snackBarBinding)

        snackBarView.doOnLayout {
            val animator = animate(
                from = hiddenPosition,
                to = shownPosition
            )

            animator.doOnEnd {
                sh.state = State.Shown
                hideAfterDelay()
            }
        }
    }

    private fun hideInternal() {
        cancelHideAfterDelay()

        val animator = animate(
            from = shownPosition,
            to = hiddenPosition
        )

        animator.doOnEnd { sh.state = State.Hidden }
    }

    private fun waitForSettling(action: () -> Unit) {
        sh.doOnNextState(action)
    }

    private fun closeAndShowInternal(action: (B) -> Unit) {
        hide()
        show(action)
    }

    private fun hideAfterDelay() {
        cancelHideAfterDelay()
        hideAfterDelayRunnable = snackBarView.postDelayed(topDelay, action = ::hide)
    }

    private fun cancelHideAfterDelay() {
        if (hideAfterDelayRunnable != null)
            snackBarView.removeCallbacks(hideAfterDelayRunnable!!)
    }

    private fun resetSnackBar() {
        snackBarView.y = hiddenPosition
        sh.state = State.Hidden
    }

    private fun animate(from: Float, to: Float): ValueAnimator {
        sh.state = State.Settling

        return ValueAnimator.ofFloat(from, to).apply {
            interpolator = LinearInterpolator()
            duration = this@SnackBarBehavior.duration

            addUpdateListener { animator ->
                snackBarView.y = animator.animatedValue as Float
            }
        }.also { it.start() }
    }

    enum class State {
        Shown,
        Hidden,
        Settling
    }

    private class StateHolder(initialState: State) {

        var state: State = initialState
            set(value) {
                field = value
                notifyStateChanged()
            }

        private var onNextState: (() -> Unit)? = null

        fun doOnNextState(action: () -> Unit) {
            onNextState = action
        }

        private fun notifyStateChanged() {
            val callback = onNextState ?: return
            onNextState = null
            callback()
        }
    }

    companion object {
        private const val DEFAULT_DURATION = 500L
        private const val DEFAULT_TOP_DELAY = 3000L
    }
}
