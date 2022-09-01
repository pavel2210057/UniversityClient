package com.universityclient.app.interactor.common

import com.moodle.client.result.ApiResult

sealed class DataResult<T> {

    fun isSuccess() = this is Success
    fun isFailure() = this is Failure

    data class Success<T>(val data: T) : DataResult<T>()

    data class Failure<T>(val cause: Exception) : DataResult<T>()

    companion object {
        fun<T> success(data: T) = Success(data)
        fun<T> failure(cause: Exception) = Failure<T>(cause)
    }
}

fun<T> ApiResult<T>.asDataResult() = when (this) {
    is ApiResult.Success -> DataResult.success(data)
    is ApiResult.Failure -> DataResult.failure(cause)
}

fun<T> T.asDataResult() = DataResult.success(this)
