package com.universityclient.app.interactor.common.validation

sealed class ValidationState {

    fun isSuccess() = this is Success
    fun isFailure() = this is Failure

    object Empty : ValidationState()

    object Success : ValidationState()

    data class Failure(val error: ValidationError) : ValidationState()

    companion object {
        fun empty() = Empty
        fun success() = Success
        fun failure(error: ValidationError) = Failure(error)
    }
}
