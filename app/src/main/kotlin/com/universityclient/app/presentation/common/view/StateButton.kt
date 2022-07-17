package com.universityclient.app.presentation.common.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.universityclient.app.R
import com.universityclient.app.presentation.common.effect.ShimmerEffect

class StateButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    style: Int = 0
) : AppCompatButton(context, attrs, style) {

    var state: State = State.Enabled
    set(value) {
        field = value
        setStateInternal(value)
    }

    private val shimmerEffect by lazy { ShimmerEffect(view = this) }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateButton)

        state = when(typedArray.getInt(R.styleable.StateButton_initialState, 0)) {
            0 -> State.Enabled
            1 -> State.Disabled
            2 -> State.Loading
            else -> error("Unexpected behavior")
        }

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (state == State.Loading) {
            shimmerEffect.draw(canvas, drawingTime)
            invalidate()
        }
    }

    private fun setStateInternal(state: State) {
        isEnabled = when (state) {
            State.Loading, State.Disabled -> false
            State.Enabled -> true
        }
    }

    enum class State {
        Enabled,
        Disabled,
        Loading,
    }
}

fun StateButton.enabled() { state = StateButton.State.Enabled }

fun StateButton.disabled() { state = StateButton.State.Disabled }

fun StateButton.loading() { state = StateButton.State.Loading }
