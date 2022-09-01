package com.universityclient.domain

data class User(
    val id: String,
    val username: String,
    val fullname: String,
    val email: String,
    val department: String,
    val institution: String,
    val firstAccess: Long,
    val lastAccess: Long,
    val language: String,
    val timeZone: String,
    val country: String,
    val city: String,
    val smallAvatarUrl: String,
    val avatarUrl: String
)
