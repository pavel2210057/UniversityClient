package com.universityclient.domain

data class User(
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val avatarUrl: String,
    val homePageUrl: String,
    val isAdmin: Boolean
)
