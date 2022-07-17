package com.universityclient.app.presentation.common.effect

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.universityclient.app.R
import com.universityclient.app.presentation.common.ext.dp
import com.universityclient.app.presentation.common.ext.linearGradient
import com.universityclient.app.presentation.common.ext.resolveColor

class ShimmerEffect(
    private val view: View,
    private val width: Float = 120.dp(),
    private val interpolator: Interpolator = AccelerateDecelerateInterpolator(),
    private val flightTimeMills: Long = 800,
    color: Int = view.context.resolveColor(R.attr.shimmerColor),
    edgeColor: Int = view.context.resolveColor(R.attr.shimmerEdgeColor),
) {

    private val paint = Paint().apply {
        this.color = color
        this.isDither = true
        this.isAntiAlias = true

        this.shader = linearGradient(
            startX = -width,
            colors = listOf(edgeColor, color, edgeColor)
        )
    }

    fun draw(canvas: Canvas, timeElapsed: Long) {
        val currentAnimState = interpolator.getInterpolation(
            timeElapsed.toFloat() % flightTimeMills / flightTimeMills
        )

        canvas.save()

        canvas.translate((view.width + width) * currentAnimState, 0f)
        canvas.drawPaint(paint)

        canvas.restore()
    }
}
