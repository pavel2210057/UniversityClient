package com.moodle.client.internal.api

interface Transformable<T> {

    fun transform(): T
}
