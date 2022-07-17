package com.universityclient.app.validation

import com.universityclient.app.interactor.common.validation.ValidationSubject
import com.universityclient.app.interactor.common.validation.rule.EmptyStringError
import com.universityclient.app.interactor.common.validation.rule.NotEmptyValidationRule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class NotEmptyTest {

    @Test
    fun `on valid data, onSuccess is called`() {
        val validator = NotEmptyValidationRule()
        val subject = mock<ValidationSubject<String>>().stub {
            on { value } doReturn "not empty"
        }

        val result = validator.validate(subject)

        assertTrue(result)
        verify(subject, times(1)).onValidationSuccess()
    }

    @Test
    fun `on invalid data, onFailure is called`() {
        val validator = NotEmptyValidationRule()
        val subject = mock<ValidationSubject<String>>().stub {
            on(ValidationSubject<String>::value) doReturn ""
        }

        val result = validator.validate(subject)

        assertFalse(result)
        verify(subject, times(1)).onValidationError(EmptyStringError)
    }
}
