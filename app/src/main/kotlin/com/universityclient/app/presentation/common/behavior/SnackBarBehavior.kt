package com.universityclient.app.presentation.common.behavior

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import com.google.android.material.behavior.SwipeDismissBehavior
import com.universityclient.app.R
import com.universityclient.app.databinding.ViewSnackAlertBinding

class SnackBarBehavior(
    private val rootView: ViewGroup,
    @LayoutRes private val layoutRes: Int = R.layout.view_snack_alert
) {

    fun show(title: String, desc: String) {
        ViewSnackAlertBinding.inflate(LayoutInflater.from(rootView.context), rootView, false).apply {
            root.doOnLayout { view ->
                view.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                    behavior = object : SwipeDismissBehavior<View>() {

                        override fun canSwipeDismissView(requiredView: View): Boolean {
                            return requiredView === view
                        }
                    }
                }

                val start = -view.height.toFloat()
                val end = 0f

                ValueAnimator.ofFloat(start, end).apply {
                    interpolator = LinearInterpolator()
                    duration = 300

                    addUpdateListener { animator ->
                        view.y = (animator.animatedValue as Float)
                    }
                }.start()
            }

            textViewTitle.text = title
            textViewDesc.text = desc

            this@SnackBarBehavior.rootView.addView(this.root)
        }
    }
}
