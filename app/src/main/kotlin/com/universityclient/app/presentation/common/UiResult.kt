package com.universityclient.app.presentation.common

sealed class UiResult<T> {

    fun isSuccess() = this is Success
    fun isEmpty() = this is Empty
    fun isLoading() = this is Loading
    fun isFailure() = this is Failure

    data class Success<T>(val data: T) : UiResult<T>()

    class Empty<T> : UiResult<T>()

    class Loading<T> : UiResult<T>()

    data class Failure<T>(val cause: Exception) : UiResult<T>()

    companion object {
        fun<T> success(data: T) = Success(data)
        fun<T> empty() = Empty<T>()
        fun<T> loading() = Loading<T>()
        fun<T> failure(cause: Exception) = Failure<T>(cause)
    }
}
