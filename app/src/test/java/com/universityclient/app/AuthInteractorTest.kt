@file:OptIn(ExperimentalCoroutinesApi::class)

package com.universityclient.app

import com.moodle.client.result.ApiResult
import com.universityclient.app.common.ext.exception
import com.universityclient.app.data.repository.TokenRepository
import com.universityclient.app.interactor.auth.AuthInteractor
import com.universityclient.app.interactor.common.validation.ValidationError
import com.universityclient.app.interactor.common.validation.ValidationSubject
import com.universityclient.app.interactor.common.validation.rule.NotEmptyValidationRule
import com.universityclient.domain.Token
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class AuthInteractorTest {

    @Test
    fun `on correct data, login() returns success`() = runTest {
        val usernameSubject = mock<ValidationSubject<String>>().validationSubjectStub()
        val passwordSubject = mock<ValidationSubject<String>>().validationSubjectStub()

        val tokenRepository = mock<TokenRepository>().stub {
            onBlocking {
                loadToken(usernameSubject.value, passwordSubject.value)
            } doReturn ApiResult.success(Token(""))
        }

        val validationRule = mock<NotEmptyValidationRule>().stub {
            on { validate(usernameSubject, passwordSubject) } doReturn true
        }

        val authInteractor = AuthInteractor(tokenRepository, validationRule)

        val result = authInteractor.login(usernameSubject, passwordSubject)

        assertTrue(result.isSuccess)
        verify(tokenRepository, times(1)).loadToken(any(), any())
    }

    @Test
    fun `on invalid data, login() returns failure`() = runTest {
        val usernameSubject = mock<ValidationSubject<String>>().validationSubjectStub()
        val passwordSubject = mock<ValidationSubject<String>>().validationSubjectStub()

        val tokenRepository = mock<TokenRepository>()

        val validationRule = mock<NotEmptyValidationRule>().stub {
            on { validate(usernameSubject, passwordSubject) } doReturn false
        }

        val authInteractor = AuthInteractor(tokenRepository, validationRule)

        val result = authInteractor.login(usernameSubject, passwordSubject)

        assertTrue(result.isFailure)
        assertTrue(result.exception() is ValidationError)
        verifyNoInteractions(tokenRepository)
    }

    @Test
    fun `on incorrect data, login() returns failure`() = runTest {
        val usernameSubject = mock<ValidationSubject<String>>().validationSubjectStub()
        val passwordSubject = mock<ValidationSubject<String>>().validationSubjectStub()

        val tokenRepository = mock<TokenRepository>().stub {
            onBlocking {
                loadToken(usernameSubject.value, passwordSubject.value)
            } doReturn ApiResult.failure(Exception())
        }

        val validationRule = mock<NotEmptyValidationRule>().stub {
            on { validate(usernameSubject, passwordSubject) } doReturn true
        }

        val authInteractor = AuthInteractor(tokenRepository, validationRule)

        val result = authInteractor.login(usernameSubject, passwordSubject)

        assertTrue(result.isFailure)
        verify(tokenRepository, times(1)).loadToken(any(), any())
    }

    private fun ValidationSubject<String>.validationSubjectStub(value: String = "") = stub {
        on { this.value } doReturn value
    }
}
