package com.moodle.client.internal

import com.moodle.client.result.ApiException
import io.ktor.client.request.*
import io.ktor.http.content.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal sealed class ApiError {
    abstract val key: String
    abstract val message: String

    object UNKNOWN_ERROR : ApiError() {
        override val key = "Unknown error"
        override val message = "Unknown error"
    }
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

internal fun ApiError.asApiException(
    request: HttpRequest,
    responseText: String,
    parseException: Exception?
): ApiException {
    val message = "Message: $message"
        .appendRequest(request)
        .appendResponse(responseText)
        .appendException(parseException)

    return when (key) {
        ApiException.InvalidLoginException.KEY ->
            ApiException.InvalidLoginException(message = message)
        ApiException.InvalidTokenException.KEY ->
            ApiException.InvalidTokenException(message = message)
        ApiException.ServiceUnavailableException.KEY ->
            ApiException.ServiceUnavailableException(message = message)
        ApiException.IncorrectFunctionException.KEY ->
            ApiException.IncorrectFunctionException(message = message)
        else -> ApiException.UnknownException(key = key, message = message)
    }
}

private fun String.appendRequest(request: HttpRequest): String {
    val content = request.content
    val bodyText = if (content is OutgoingContent.ByteArrayContent)
        content.bytes().decodeToString()
    else
        "Empty Body"

    return """
        $this
        Url: ${request.url}
        Method: ${request.method.value}
        Headers: ${request.headers}
        Body: $bodyText
    """.trimIndent()
}

private fun String.appendResponse(response: String): String {
    return """
        $this
        Response: $response
    """.trimIndent()
}

private fun String.appendException(parseException: Exception?): String {
    return """
        $this
        Cause: ${parseException?.cause}
        Message: ${parseException?.message}
    """.trimIndent()
}
