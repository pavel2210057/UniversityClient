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

/**
 * properties delegated by lifecycleAware, will be
 * automatically cleared when the lifecycle of the
 * lifecycle owner is Lifecycle.State.DESTROYED
 *
 * Note: if the current owner's state is DESTROYED,
 *       then will be returned value from the
 *       initialization block
 */
fun<T> LifecycleOwner.lifecycleAware(block: () -> T) = LifecycleAwareDelegate(this, block)
