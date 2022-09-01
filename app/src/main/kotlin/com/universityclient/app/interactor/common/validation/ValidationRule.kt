package com.universityclient.app.interactor.common.validation

interface ValidationRule<T> {

    fun validate(subject: ValidationSubject<T>): Boolean
}

fun<T> ValidationRule<T>.validate(vararg subjects: ValidationSubject<T>): Boolean {
    return subjects.fold(true) { acc, subject -> validate(subject) && acc }
}
