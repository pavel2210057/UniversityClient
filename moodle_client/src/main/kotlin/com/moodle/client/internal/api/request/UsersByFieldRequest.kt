package com.moodle.client.internal.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UsersByFieldRequest(
    @SerialName("field") val field: String,
    @SerialName("values[]") val values: List<String>
)
