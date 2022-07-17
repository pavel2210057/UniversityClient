package com.universityclient.app.interactor.common.validation

import kotlinx.coroutines.flow.MutableStateFlow

interface ValidationSubject<T> {

    val value: T

    fun onValidationSuccess()

    fun onValidationError(validationError: ValidationError)
}

inline fun<T> T.asValidationSubject(
    crossinline onSuccess: () -> Unit = {},
    crossinline onFailure: (ValidationError) -> Unit = {}
) = object : ValidationSubject<T> {

    override val value: T
        get() = this@asValidationSubject

    override fun onValidationSuccess() {
        onSuccess()
    }

    override fun onValidationError(validationError: ValidationError) {
        onFailure(validationError)
    }
}

fun<T> T.emptyValidation() = this.asValidationSubject()

infix fun<T> T.validateTo(flow: MutableStateFlow<ValidationState>) =
    this.asValidationSubject(
        onSuccess = { flow.value = ValidationState.success() },
        onFailure = { error -> flow.value = ValidationState.failure(error) }
    )
