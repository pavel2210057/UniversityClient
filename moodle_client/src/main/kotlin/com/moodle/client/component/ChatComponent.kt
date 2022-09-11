package com.moodle.client.component

import com.moodle.client.result.ApiResult
import com.universityclient.domain.Chat

interface ChatComponent {

    suspend fun getChats(userId: String): ApiResult<List<Chat>>

    suspend fun getChat(
        userId: String,
        chatId: String,
        includeContactRequests: Boolean,
        includePrivacyInfo: Boolean
    ): ApiResult<Chat>
}
