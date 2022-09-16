package com.universityclient.app.data.repository

import com.moodle.client.component.ChatComponent
import com.universityclient.app.data.db.dao.ChatDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatDao: ChatDao,
    private val chatComponent: ChatComponent
) {

    suspend fun getAndLoadChats() = withContext(Dispatchers.IO) {
        //TODO
    }

    suspend fun getCachedChats() = withContext(Dispatchers.IO) {
        chatDao.getChats()
    }

    suspend fun getCachedChat(chatId: String) = withContext(Dispatchers.IO) {
        chatDao.getChatByIdOrNull(chatId)
    }

    suspend fun loadChats(userId: String) = withContext(Dispatchers.IO) {
        chatComponent.getChats(userId)
    }

    suspend fun loadChat(
        userId: String,
        chatId: String,
        includeContactRequests: Boolean,
        includePrivacyInfo: Boolean
    ) = withContext(Dispatchers.IO) {
        chatComponent.getChat(
            userId = userId,
            chatId = chatId,
            includeContactRequests = includeContactRequests,
            includePrivacyInfo = includePrivacyInfo
        )
    }
}
