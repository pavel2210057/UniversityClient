package com.universityclient.app.interactor.common.validation.rule

import com.universityclient.app.interactor.common.validation.ValidationError
import com.universityclient.app.interactor.common.validation.ValidationRule
import com.universityclient.app.interactor.common.validation.ValidationSubject
import javax.inject.Inject

class NotEmptyValidationRule @Inject constructor() : ValidationRule<String> {

    override fun validate(subject: ValidationSubject<String>): Boolean {
        val isValid = subject.value.isNotEmpty()
        if (isValid) {
            subject.onValidationSuccess()
            return true
        }

        subject.onValidationError(EmptyStringError)
        return false
    }
}

object EmptyStringError : ValidationError()
