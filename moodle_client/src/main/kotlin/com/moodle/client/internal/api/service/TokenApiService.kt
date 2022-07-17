package com.moodle.client.internal.api.service

import com.universityclient.domain.Token
import com.moodle.client.internal.api.asApiResultTransformable
import com.moodle.client.internal.api.requestModifier.MobileServiceRequestModifier
import com.moodle.client.internal.api.requestModifier.modifyWith
import com.moodle.client.internal.api.request.TokenRequest
import com.moodle.client.internal.api.response.TokenResponse
import com.moodle.client.result.ApiResult
import io.ktor.client.*
import io.ktor.client.plugins.resources.*

internal class TokenApiService(
    private val client: HttpClient
) {

    suspend fun getToken(tokenRequest: TokenRequest): ApiResult<Token> {
        val tokenResponse = client.get(tokenRequest) {

            modifyWith(MobileServiceRequestModifier) {
                needMobileService = true
            }
        }

        return tokenResponse.asApiResultTransformable<Token, TokenResponse>()
    }
}
