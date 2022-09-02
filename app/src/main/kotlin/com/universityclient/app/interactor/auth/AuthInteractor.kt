package com.universityclient.app.interactor.auth

import com.universityclient.app.data.repository.TokenRepository
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.app.interactor.common.validation.ValidationError
import com.universityclient.app.interactor.common.validation.ValidationSubject
import com.universityclient.app.interactor.common.validation.rule.NotEmptyValidationRule
import com.universityclient.app.interactor.common.validation.validate
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val notEmptyValidationRule: NotEmptyValidationRule
) {

    suspend fun login(
        usernameSubject: ValidationSubject<String>,
        passwordSubject: ValidationSubject<String>
    ): DataResult<Unit> {
        val isDataCorrect = validateLoginData(usernameSubject, passwordSubject)
        if (!isDataCorrect)
            return DataResult.failure(ValidationError())

        val username = usernameSubject.value
        val password = passwordSubject.value

        return when(val tokenResult = tokenRepository.loadToken(username, password)) {
            is DataResult.Success -> DataResult.success(Unit)
            is DataResult.Failure -> DataResult.failure(tokenResult.cause)
        }
    }

    suspend fun logout() {
        tokenRepository.clearToken()
    }

    private fun validateLoginData(
        username: ValidationSubject<String>,
        password: ValidationSubject<String>
    ): Boolean = notEmptyValidationRule.validate(username, password)
}
