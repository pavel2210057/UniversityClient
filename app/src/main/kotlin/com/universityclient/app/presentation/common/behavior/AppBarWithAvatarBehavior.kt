package com.universityclient.app.presentation.common.behavior

import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

class AppBarWithAvatarBehavior : CoordinatorLayout.Behavior<View>() {

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: View,
        ev: MotionEvent
    ): Boolean {
        '1'.code
        return super.onInterceptTouchEvent(parent, child, ev)
    }
}
