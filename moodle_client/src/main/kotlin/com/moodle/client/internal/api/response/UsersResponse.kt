package com.moodle.client.internal.api.response

import com.moodle.client.internal.api.Transformable
import com.universityclient.domain.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserResponse(
    @SerialName("id") val id: Int,
    @SerialName("username") val username: String,
    @SerialName("fullname") val fullname: String,
    @SerialName("email") val email: String,
    @SerialName("department") val department: String,
    @SerialName("institution") val institution: String,
    @SerialName("firstaccess") val firstAccess: Long,
    @SerialName("lastaccess") val lastAccess: Long,
    @SerialName("lang") val language: String,
    @SerialName("timezone") val timeZone: String,
    @SerialName("country") val country: String,
    @SerialName("city") val city: String,
    @SerialName("profileimageurlsmall") val smallAvatarUrl: String,
    @SerialName("profileimageurl") val avatarUrl: String
) : Transformable<User> {

    override fun transform() = User(
        id = id.toString(),
        username = username,
        fullname = fullname,
        email = email,
        department = department,
        institution = institution,
        firstAccess = firstAccess,
        lastAccess = lastAccess,
        language = language,
        timeZone = timeZone,
        country = country,
        city = city,
        smallAvatarUrl = smallAvatarUrl,
        avatarUrl = avatarUrl
    )
}
