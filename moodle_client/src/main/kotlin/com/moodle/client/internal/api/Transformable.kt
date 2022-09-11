package com.moodle.client.internal.api

internal interface Transformable<T> {

    fun transform(): T
}
