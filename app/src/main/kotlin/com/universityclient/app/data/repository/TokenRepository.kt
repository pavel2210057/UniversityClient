package com.universityclient.app.data.repository

import com.moodle.client.component.TokenComponent
import com.moodle.client.result.ApiResult
import com.universityclient.app.data.db.dao.TokenDao
import com.universityclient.app.data.db.model.TokenEntity
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.domain.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val tokenComponent: TokenComponent,
    private val tokenDao: TokenDao
) {

    suspend fun loadToken(
        username: String,
        password: String
    ): DataResult<Token> = withContext(Dispatchers.IO) {
        return@withContext when(val tokenResult = tokenComponent.getToken(username, password)) {
            is ApiResult.Success -> {
                tokenDao.updateToken(
                    tokenEntity = tokenResult.data.asTokenEntity()
                )
                DataResult.success(tokenResult.data)
            }
            is ApiResult.Failure -> {
                DataResult.failure(tokenResult.cause)
            }
        }
    }

    suspend fun getTokenOrNull(): Token? = withContext(Dispatchers.IO) {
        tokenDao.getTokenOrNull()?.transform()
    }

    suspend fun clearToken() = withContext(Dispatchers.IO) {
        tokenDao.clearTokens()
    }

    private fun Token.asTokenEntity() = TokenEntity(token = token)
}
