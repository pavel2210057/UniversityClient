package com.moodle.client.internal.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenRequest(
    @SerialName("username") val username: String,
    @SerialName("password") val password: String
)
