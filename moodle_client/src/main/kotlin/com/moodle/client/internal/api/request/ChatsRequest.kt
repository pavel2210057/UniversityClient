package com.moodle.client.internal.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ChatsRequest(
    @SerialName("userid") val userId: String
)
