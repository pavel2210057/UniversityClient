package com.moodle.client.internal.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("userid") val userId: String,
    @SerialName("conversationid") val chatId: String,
    @SerialName("includecontactrequests") val includeContactRequests: Boolean,
    @SerialName("includeprivecyinfo") val includePrivacyInfo: Boolean
)
