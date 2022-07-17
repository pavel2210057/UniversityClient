package com.universityclient.app.presentation.common.ext

import android.content.res.Resources

fun Int.dp() = this * screenDensity

fun Int.sp() = this * screenScaledDensity

private val screenDensity: Float
    get() = Resources.getSystem().displayMetrics.density

private val screenScaledDensity: Float
    get() = Resources.getSystem().displayMetrics.scaledDensity
