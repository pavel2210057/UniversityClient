package com.universityclient.app.interactor.auth

import com.moodle.client.result.ApiResult
import com.universityclient.app.data.repository.TokenRepository
import com.universityclient.app.interactor.common.validation.ValidationError
import com.universityclient.app.interactor.common.validation.ValidationSubject
import com.universityclient.app.interactor.common.validation.rule.NotEmptyValidationRule
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val notEmptyValidationRule: NotEmptyValidationRule
) {

    suspend fun login(
        usernameSubject: ValidationSubject<String>,
        passwordSubject: ValidationSubject<String>
    ): Result<Unit> {
        val isDataCorrect = validateLoginData(usernameSubject, passwordSubject)
        if (!isDataCorrect)
            return Result.failure(ValidationError())

        val username = usernameSubject.value
        val password = passwordSubject.value

        return when(val tokenResult = tokenRepository.loadToken(username, password)) {
            is ApiResult.Success -> Result.success(Unit)
            is ApiResult.Failure -> Result.failure(tokenResult.throwable)
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
