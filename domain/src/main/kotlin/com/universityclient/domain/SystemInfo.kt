package com.universityclient.domain

data class SystemInfo(
    val siteId: String,
    val siteName: String,
    val siteUrl: String,
    val language: Language,
    val canManageOwnFiles: Boolean,
    val maxUploadFileSize: Long,
    val themeName: String
)
