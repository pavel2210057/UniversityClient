package com.moodle.client.internal.api.response

import com.moodle.client.internal.api.Transformable
import com.universityclient.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<UserResponse>
): Transformable<List<User>> {

    @Serializable
    data class UserResponse(
        @SerialName("id") val id: String,
        @SerialName("username") val username: String,
        @SerialName("fullname") val fullname: String,
        @SerialName("email") val email: String,
        @SerialName("department") val department: String,
        @SerialName("institution") val institution: String,
        @SerialName("firstaccess") val firstAccess: Long,
        @SerialName("lastaccess") val lastAccess: Long,
        @SerialName("language") val language: String,
        @SerialName("timezone") val timeZone: String,
        @SerialName("country") val country: String,
        @SerialName("city") val city: String,
        @SerialName("smallAvatarUrl") val smallAvatarUrl: String,
        @SerialName("avatarUrl") val avatarUrl: String
    )

    override fun transform() = users.map { user ->
        User(
            id = user.id,
            username = user.username,
            fullname = user.fullname,
            email = user.email,
            department = user.department,
            institution = user.institution,
            firstAccess = user.firstAccess,
            lastAccess = user.lastAccess,
            language = user.language,
            timeZone = user.timeZone,
            country = user.country,
            city = user.city,
            smallAvatarUrl = user.smallAvatarUrl,
            avatarUrl = user.avatarUrl
        )
    }
}
