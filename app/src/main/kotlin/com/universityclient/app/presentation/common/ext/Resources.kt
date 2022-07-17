package com.universityclient.app.presentation.common.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes

@ColorRes
fun Context.resolveColor(@AttrRes attr: Int): Int {
    return this.theme.resolveAttribute(attr)
}

fun Resources.Theme.resolveAttribute(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    if (!resolveAttribute(attr, typedValue, true))
        error("Attribute with id $attr wasn't found")
    return typedValue.data
}
