package com.moodle.client.internal.api.requestModifier

import io.ktor.client.request.*

internal interface RequestModifier<TConfig> {

    suspend fun modify(httpRequestBuilder: HttpRequestBuilder): HttpRequestBuilder
}

internal interface RequestModifierBuilder<TConfig, TRequestModifier : RequestModifier<TConfig>> {

    fun build(block: TConfig.() -> Unit): RequestModifier<TConfig>
}

internal suspend fun<TConfig, TRequestModifier : RequestModifier<TConfig>> HttpRequestBuilder.modifyWith(
    modifierBuilder: RequestModifierBuilder<TConfig, TRequestModifier>,
    block: TConfig.() -> Unit = {}
) {
    modifierBuilder.build(block).modify(this)
}
