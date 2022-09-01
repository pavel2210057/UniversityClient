package com.moodle.client.internal.api.request

import com.moodle.client.internal.api.constant.RestUrl
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Resource(RestUrl.REST)
data class UsersByFieldRequest(
    @SerialName("field") val field: String,
    @SerialName("values") val values: List<String>
)
