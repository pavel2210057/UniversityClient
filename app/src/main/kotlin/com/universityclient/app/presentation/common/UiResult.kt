package com.universityclient.app.presentation.common

sealed class UiResult<T, F> {

    fun isSuccess() = this is Success
    fun isEmpty() = this is Empty
    fun isLoading() = this is Loading
    fun isFailure() = this is Failure

    data class Success<T, F>(val data: T) : UiResult<T, F>()

    class Empty<T, F> : UiResult<T, F>()

    class Loading<T, F> : UiResult<T, F>()

    data class Failure<T, F>(val cause: F) : UiResult<T, F>()

    companion object {
        fun<T, F> success(data: T) = Success<T, F>(data)
        fun<T, F> empty() = Empty<T, F>()
        fun<T, F> loading() = Loading<T, F>()
        fun<T, F> failure(cause: F) = Failure<T, F>(cause)
    }
}
