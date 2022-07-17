package com.universityclient.app.common.ext

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

class KReference0Delegate<T>(
    private val reference: KMutableProperty0<T>
) {

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T
    ) {
        reference.setValue(thisRef, property, value)
    }

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): T = reference.getValue(thisRef, property)
}

fun<T> ref(reference: KMutableProperty0<T>) = KReference0Delegate(reference)

fun<T> ref(block: () -> KMutableProperty0<T>) = KReference0Delegate(block())
