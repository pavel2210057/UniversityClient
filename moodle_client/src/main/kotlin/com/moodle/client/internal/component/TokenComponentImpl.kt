package com.moodle.client.internal.component

import com.moodle.client.component.TokenComponent
import com.universityclient.domain.Token
import com.moodle.client.internal.api.service.TokenApiService
import com.moodle.client.internal.api.request.TokenRequest
import com.moodle.client.result.ApiResult
import javax.inject.Inject

internal class TokenComponentImpl @Inject constructor(
    private val tokenService: TokenApiService
) : TokenComponent {

    override suspend fun getToken(username: String, password: String): ApiResult<Token> {
        val request = TokenRequest(
            username = username,
            password = password
        )

        return tokenService.getToken(request)
    }
}
