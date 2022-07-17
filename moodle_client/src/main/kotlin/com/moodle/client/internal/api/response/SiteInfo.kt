package com.moodle.client.internal.api.response

import com.universityclient.domain.SystemInfo
import com.universityclient.domain.User
import com.universityclient.domain.asLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SiteInfo(
    @SerialName("sitename") val siteName: String,
    @SerialName("username") val username: String,
    @SerialName("firstname") val firstName: String,
    @SerialName("lastname") val lastName: String,
    @SerialName("fullname") val fullName: String,
    @SerialName("lang") val language: String,
    @SerialName("userid") val userId: String,
    @SerialName("siteurl") val siteUrl: String,
    @SerialName("userpictureurl") val avatarUrl: String,
    @SerialName("usercanmanageownfiles") val userCanManageOwnFiles: Boolean,
    @SerialName("usermaxuploadfilesize") val userMaxUploadFileSize: Long,
    @SerialName("userhomepage") val userHomePage: String,
    @SerialName("siteid") val siteId: String,
    @SerialName("userissiteadmin") val userIsSiteAdmin: Boolean,
    @SerialName("theme") val themeName: String,
) {

    fun transformAsUser() = User(
        id = userId,
        username = username,
        firstName = firstName,
        lastName = lastName,
        fullName = fullName,
        avatarUrl = avatarUrl,
        homePageUrl = userHomePage,
        isAdmin = userIsSiteAdmin
    )

    fun transformAsSystemInfo() = SystemInfo(
        siteId = siteId,
        siteName = siteName,
        siteUrl = siteUrl,
        language = language.asLanguage(),
        canManageOwnFiles = userCanManageOwnFiles,
        maxUploadFileSize = userMaxUploadFileSize,
        themeName = themeName
    )
}
