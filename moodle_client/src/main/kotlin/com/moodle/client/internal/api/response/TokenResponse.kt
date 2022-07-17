package com.moodle.client.internal.api.response

import com.universityclient.domain.Token
import com.moodle.client.internal.api.Transformable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponse(
    @SerialName("token") val token: String
) : Transformable<Token> {

    override fun transform() = Token(
        token = token
    )
}
