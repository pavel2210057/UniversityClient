package com.universityclient.app.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.universityclient.app.data.db.constant.TableNames

@Entity(
    tableName = TableNames.ChatTable
)
data class ChatEntity(
    @PrimaryKey val id: String,
    val isMuted: Boolean,
    val isFavorite: Boolean,
    val isRead: Boolean,
    val unreadMessageCount: Int,
    @Embedded(prefix = "member") val members: List<ChatMember>,
    @Embedded(prefix = "message") val messages: List<ChatMessage>
) {

    data class ChatMember(
        val id: String,
        val fullName: String,
        val profileUrl: String,
        val avatarUrl: String,
        val isOnline: Boolean,
        val isBlocked: Boolean,
        val isDeleted: Boolean
    )

    data class ChatMessage(
        val id: String,
        val userFromId: String,
        val text: String,
        val createdAt: Long
    )
}
