package com.moodle.client.internal

import com.moodle.client.result.ApiException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal sealed class ApiError {
    abstract val key: String
    abstract val message: String
}

@Serializable
internal data class FunctionCallError(
    @SerialName("errorcode") override val key: String,
    @SerialName("message") override val message: String
) : ApiError()

@Serializable
internal data class LoginError(
    @SerialName("errorcode") override val key: String,
    @SerialName("error") override val message: String
) : ApiError()

internal fun ApiError.asApiException(): ApiException {
    return when (key) {
        ApiException.InvalidLoginException.KEY -> ApiException.InvalidLoginException(message = message)
        ApiException.InvalidTokenException.KEY -> ApiException.InvalidTokenException(message = message)
        ApiException.ServiceUnavailableException.KEY -> ApiException.ServiceUnavailableException(message = message)
        ApiException.IncorrectFunctionException.KEY -> ApiException.IncorrectFunctionException(message = message)
        else -> ApiException.UnknownException(key = key, message = message)
    }
}
