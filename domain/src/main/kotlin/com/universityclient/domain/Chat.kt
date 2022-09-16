package com.universityclient.domain

data class Chat(
    val id: String,
    val name: String,
    val isMuted: Boolean,
    val isFavorite: Boolean,
    val isRead: Boolean,
    val unreadMessageCount: Int,
    val members: List<ChatMember>,
    val messages: List<ChatMessage>
)

data class ChatMember(
    val id: String,
    val fullName: String,
    val profileUrl: String,
    val avatarUrl: String,
    val smallAvatarUrl: String,
    val isOnline: Boolean,
    val showOnlineStatus: Boolean,
    val isBlocked: Boolean,
    val isContact: Boolean,
    val isDeleted: Boolean,
    val canMessageIfBlocked: Boolean,
    val canMessage: Boolean
)

data class ChatMessage(
    val id: String,
    val userFromId: String,
    val text: String,
    val createdAt: Long
)
