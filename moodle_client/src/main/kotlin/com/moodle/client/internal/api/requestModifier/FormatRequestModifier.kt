package com.moodle.client.internal.api.requestModifier

import io.ktor.client.request.*
import javax.inject.Inject

class FormatRequestModifier @Inject constructor() : RequestModifier<Unit> {

    override suspend fun modify(httpRequestBuilder: HttpRequestBuilder): HttpRequestBuilder {
        return httpRequestBuilder.apply {
            parameter(REST_FORMAT_PARAMETER_NAME, REST_FORMAT_PARAMETER_VALUE)
        }
    }

    companion object {
        private const val REST_FORMAT_PARAMETER_NAME = "moodlewsrestformat"
        private const val REST_FORMAT_PARAMETER_VALUE = "json"
    }
}
