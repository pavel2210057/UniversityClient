package com.moodle.client.component

import com.universityclient.domain.Token
import com.moodle.client.result.ApiResult

interface TokenComponent {

    suspend fun getToken(username: String, password: String): ApiResult<Token>
}
