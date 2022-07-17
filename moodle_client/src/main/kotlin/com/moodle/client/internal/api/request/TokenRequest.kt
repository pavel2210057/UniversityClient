package com.moodle.client.internal.api.request

import com.moodle.client.internal.api.constant.LoginUrl
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Resource(LoginUrl.TOKEN)
data class TokenRequest(
    @SerialName("username") val username: String,
    @SerialName("password") val password: String
)
