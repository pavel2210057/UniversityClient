package com.universityclient.app.presentation.common.ext

import android.graphics.LinearGradient
import android.graphics.Shader

fun linearGradient(
    startX: Float = 0f,
    startY: Float = 0f,
    endX: Float = 0f,
    endY: Float = 0f,
    colors: List<Int>,
    positions: FloatArray = spreadFloatArray(colors.size),
    tileMode: Shader.TileMode = Shader.TileMode.CLAMP
) = LinearGradient(
    startX, startY, endX, endY, colors.toIntArray(), positions, tileMode
)

private fun spreadFloatArray(size: Int) = FloatArray(size) { index ->
    1f / (size - 1) * index
}
