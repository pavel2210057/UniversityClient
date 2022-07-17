package com.moodle.client.result

sealed class ApiResult<T> {

    fun isSuccess() = this is Success

    fun isFailure() = this is Failure

    data class Success<T>(val data: T) : ApiResult<T>()

    data class Failure<T>(val throwable: Throwable) : ApiResult<T>() {

        companion object {
            val UNKNOWN_ERROR = RuntimeException("Unknown error!")
        }
    }

    companion object {

        fun<T> success(data: T) = Success(data)

        fun<T> failure(t: Throwable) = Failure<T>(t)
    }
}
