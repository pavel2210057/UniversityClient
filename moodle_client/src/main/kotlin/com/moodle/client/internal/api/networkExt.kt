package com.moodle.client.internal.api

import com.moodle.client.internal.ApiError
import com.moodle.client.internal.FunctionCallError
import com.moodle.client.internal.LoginError
import com.moodle.client.internal.asApiException
import com.moodle.client.result.ApiResult
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json
    get() = Json {
        ignoreUnknownKeys = true
    }

internal suspend inline fun<reified T> HttpResponse.asApiResult(): ApiResult<T> {
    if (!status.isSuccess())
        return status.value.errorStatusCodeToApiResult()

    val json = json
    val responseText = bodyAsText()

    val body = responseText.tryParse<T>(json)
    if (body != null)
        return ApiResult.success(body)

    val apiError = responseText.tryParseApiError(json)
    if (apiError != null)
        return ApiResult.failure(apiError.asApiException())

    return ApiResult.failure(ApiResult.Failure.UNKNOWN_ERROR)
}

private fun<T> Int.errorStatusCodeToApiResult(): ApiResult<T> {
    return when (this) {
        else -> ApiResult.failure(RuntimeException("World"))
    }
}

private fun String.tryParseApiError(json: Json): ApiError? {
    return tryParse<FunctionCallError>(json)
        ?: tryParse<LoginError>(json)
}

internal inline fun<reified T> String.tryParse(json: Json): T? {
    return try {
        json.decodeFromString(this)
    } catch (e: SerializationException) {
        System.err.println(this)
        e.printStackTrace(System.err)
        null
    } catch (e: Exception) {
        //todo report to crashlytics
        System.err.println(this)
        e.printStackTrace(System.err)
        null
    }
}
