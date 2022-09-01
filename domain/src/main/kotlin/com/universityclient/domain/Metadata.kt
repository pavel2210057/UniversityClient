package com.universityclient.domain

data class SystemMetadata(
    val userMetadata: UserMetadata,
    val siteId: String,
    val siteName: String,
    val siteUrl: String,
    val language: Language,
    val maxUploadFileSize: Long,
    val themeName: String
) {

    data class UserMetadata(
        val id: String,
        val canManageOwnFiles: Boolean,
        val isAdmin: Boolean
    )
}

