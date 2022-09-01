package com.universityclient.app.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.universityclient.app.data.db.constant.TableNames
import com.universityclient.domain.User

@Entity(
    tableName = TableNames.UserTable
)
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val fullname: String,
    val email: String,
    val smallAvatarUrl: String,
    val avatarUrl: String,
    val country: String,
    val city: String,
    val timeZone: String,
    val language: String,
    val firstAccess: Long,
    val lastAccess: Long,
    val institution: String,
    val department: String
) {

    fun transform() = User(
        id = id,
        username = username,
        fullname = fullname,
        email = email,
        smallAvatarUrl = smallAvatarUrl,
        avatarUrl = avatarUrl,
        country = country,
        city = city,
        timeZone = timeZone,
        language = language,
        firstAccess = firstAccess,
        lastAccess = lastAccess,
        institution = institution,
        department = department
    )
}
