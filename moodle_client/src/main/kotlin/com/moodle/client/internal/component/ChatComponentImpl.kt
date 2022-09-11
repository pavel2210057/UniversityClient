package com.moodle.client.internal.component

import com.moodle.client.component.ChatComponent
import com.moodle.client.internal.api.request.ChatRequest
import com.moodle.client.internal.api.request.ChatsRequest
import com.moodle.client.internal.api.service.RestService
import com.moodle.client.internal.component.ext.transformListOrFailure
import com.moodle.client.internal.component.ext.transformOrFailure
import com.moodle.client.result.ApiResult
import com.universityclient.domain.Chat
import javax.inject.Inject

internal class ChatComponentImpl @Inject constructor(
    private val restService: RestService
) : ChatComponent {

    override suspend fun getChats(userId: String): ApiResult<List<Chat>> {
        val request = ChatsRequest(
            userId = userId
        )

        return restService.loadChats(request).transformListOrFailure()
    }

    override suspend fun getChat(
        userId: String,
        chatId: String,
        includeContactRequests: Boolean,
        includePrivacyInfo: Boolean
    ): ApiResult<Chat> {
        val request = ChatRequest(
            userId = userId,
            chatId = chatId,
            includeContactRequests = includeContactRequests,
            includePrivacyInfo = includePrivacyInfo
        )

        return restService.loadChat(request).transformOrFailure()
    }
}
