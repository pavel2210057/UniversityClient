package com.moodle.client

import com.universityclient.domain.Token

fun interface TokenProvider {

    suspend fun provideToken(): Token?
}
