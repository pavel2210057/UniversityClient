package com.moodle.client.internal.api.response

import com.moodle.client.internal.api.Transformable
import com.universityclient.domain.SystemMetadata
import com.universityclient.domain.asLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MetadataResponse(
    @SerialName("sitename") val siteName: String,
    @SerialName("username") val username: String,
    @SerialName("firstname") val firstName: String,
    @SerialName("lastname") val lastName: String,
    @SerialName("fullname") val fullName: String,
    @SerialName("lang") val language: String,
    @SerialName("userid") val userId: Int,
    @SerialName("siteurl") val siteUrl: String,
    @SerialName("userpictureurl") val avatarUrl: String,
    @SerialName("usercanmanageownfiles") val userCanManageOwnFiles: Boolean,
    @SerialName("usermaxuploadfilesize") val userMaxUploadFileSize: Long,
    @SerialName("siteid") val siteId: Int,
    @SerialName("userissiteadmin") val userIsSiteAdmin: Boolean,
    @SerialName("theme") val themeName: String,
) : Transformable<SystemMetadata> {

    override fun transform() = SystemMetadata(
        userMetadata = SystemMetadata.UserMetadata(
            id = userId.toString(),
            canManageOwnFiles = userCanManageOwnFiles,
            isAdmin = userIsSiteAdmin
        ),
        siteId = siteId.toString(),
        siteName = siteName,
        siteUrl = siteUrl,
        language = language.asLanguage(),
        maxUploadFileSize = userMaxUploadFileSize,
        themeName = themeName
    )
}
