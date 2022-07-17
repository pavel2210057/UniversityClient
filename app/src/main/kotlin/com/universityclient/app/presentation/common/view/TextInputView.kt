package com.universityclient.app.presentation.common.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.universityclient.app.R
import com.google.android.material.R as MaterialR
import com.universityclient.app.presentation.common.ext.makeOutline

class TextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : TextInputLayout(context, attrs, style) {

    private val editTextView = TextInputEditText(context, attrs)

    val text: String
        get() = editTextView.text.toString()

    init {
        addView(
            editTextView,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextInputView)

        hint = typedArray.getString(R.styleable.TextInputView_hintText)

        boxBackgroundColor = typedArray.getColor(
            MaterialR.styleable.TextInputLayout_boxBackgroundColor,
            context.getColor(R.color.white)
        )

        typedArray.recycle()

        makeOutline { view, outline ->
            outline.setRoundRect(
                Rect(0, 0, view.width, view.height),
                OUTLINE_RADIUS
            )
        }
    }

    companion object {
        private const val OUTLINE_RADIUS = 3f
    }
}
