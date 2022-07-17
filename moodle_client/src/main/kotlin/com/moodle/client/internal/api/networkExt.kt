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
import java.lang.RuntimeException

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

internal suspend inline fun<reified R,
        reified T : Transformable<R>> HttpResponse.asApiResultTransformable(): ApiResult<R> {

    return when(val result = asApiResult<T>()) {
        is ApiResult.Success -> ApiResult.success(result.data.transform())
        is ApiResult.Failure -> ApiResult.failure(result.throwable)
    }
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
        null
    } catch (e: Exception) {
        //todo report to crashlytics
        null
    }
}
