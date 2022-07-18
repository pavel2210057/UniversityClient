package com.universityclient.app.presentation.common.ext

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.reflect.KProperty

class LifecycleAwareDelegate<T>(
    private val lifecycleOwner: LifecycleOwner,
    private val initBlock: () -> T
) : DefaultLifecycleObserver {

    private var value: T? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): T {
        return if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED)
            initBlock()
        else
            value ?: initBlock().also { value = it }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        value = null
    }
}

fun<T> LifecycleOwner.lifecycleAware(block: () -> T) = LifecycleAwareDelegate(this, block)
