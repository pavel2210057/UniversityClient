package com.moodle.client.internal.api.response

import com.moodle.client.internal.api.Transformable
import com.universityclient.domain.Chat
import com.universityclient.domain.ChatMember
import com.universityclient.domain.ChatMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("ismuted") val isMuted: Boolean,
    @SerialName("isfavourite") val isFavorite: Boolean,
    @SerialName("isread") val isRead: Boolean,
    @SerialName("unreadcount") val unreadMessageCount: Int,
    @SerialName("members") val members: List<ChatMemberResponse>,
    @SerialName("messages") val messages: List<ChatMessageResponse>
) : Transformable<Chat> {

    override fun transform() = Chat(
        id = id.toString(),
        name = name,
        isMuted = isMuted,
        isFavorite = isFavorite,
        isRead = isRead,
        unreadMessageCount = unreadMessageCount,
        members = members.map(ChatMemberResponse::transform),
        messages = messages.map(ChatMessageResponse::transform)
    )
}

@Serializable
data class ChatMemberResponse(
    @SerialName("id") val id: Int,
    @SerialName("fullname") val fullName: String,
    @SerialName("profileurl") val profileUrl: String,
    @SerialName("profileimageurl") val avatarUrl: String,
    @SerialName("profileimageurlsmall") val smallAvatarUrl: String,
    @SerialName("isonline") val isOnline: Boolean,
    @SerialName("showonlinestatus") val showOnlineStatus: Boolean,
    @SerialName("isblocked") val isBlocked: Boolean,
    @SerialName("iscontact") val isContact: Boolean,
    @SerialName("isdeleted") val isDeleted: Boolean,
    @SerialName("canmessageevenifblocked") val canMessageIfBlocked: Boolean,
    @SerialName("canmessage") val canMessage: Boolean
) : Transformable<ChatMember> {

    override fun transform() = ChatMember(
        id = id.toString(),
        fullName = fullName,
        profileUrl = profileUrl,
        avatarUrl = avatarUrl,
        smallAvatarUrl = smallAvatarUrl,
        isOnline = isOnline,
        showOnlineStatus = showOnlineStatus,
        isBlocked = isBlocked,
        isContact = isContact,
        isDeleted = isDeleted,
        canMessageIfBlocked = canMessageIfBlocked,
        canMessage = canMessage
    )
}

@Serializable
data class ChatMessageResponse(
    @SerialName("id") val id: Long,
    @SerialName("useridfrom") val userFromId: Int,
    @SerialName("text") val text: String,
    @SerialName("timecreated") val createdAt: Long
) : Transformable<ChatMessage> {

    override fun transform() = ChatMessage(
        id = id.toString(),
        userFromId = userFromId.toString(),
        text = text,
        createdAt = createdAt
    )
}
