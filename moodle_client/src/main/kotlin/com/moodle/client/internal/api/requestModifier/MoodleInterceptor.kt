package com.moodle.client.internal.api.requestModifier

import io.ktor.client.plugins.*

internal fun HttpSend.interceptors(
    vararg modifiers: RequestModifier<*>
) {
    intercept { httpRequestBuilder ->
        httpRequestBuilder.apply {
            modifiers.forEach { it.modify(this) }
        }
        execute(httpRequestBuilder)
    }
}
