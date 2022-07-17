package com.moodle.client.internal.api.requestModifier

import com.moodle.client.TokenProvider
import io.ktor.client.request.*
import javax.inject.Inject

internal class TokenRequestModifier @Inject constructor(
    private val tokenProvider: TokenProvider
) : RequestModifier<Unit> {

    override suspend fun modify(httpRequestBuilder: HttpRequestBuilder): HttpRequestBuilder {
        return httpRequestBuilder.apply {
            val token = tokenProvider.provideToken() ?: return@apply
            parameter(TOKEN_PARAMETER_NAME, token)
        }
    }

    companion object {
        private const val TOKEN_PARAMETER_NAME = "wstoken"
    }
}
