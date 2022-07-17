package com.universityclient.app.data.repository

import com.moodle.client.component.TokenComponent
import com.universityclient.domain.Token
import com.moodle.client.result.ApiResult
import com.universityclient.app.data.db.dao.TokenDao
import com.universityclient.app.data.db.model.TokenEntity
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
    ): ApiResult<Token> = withContext(Dispatchers.IO) {
        val tokenResult = tokenComponent.getToken(username, password)

        if (tokenResult is ApiResult.Success)
            tokenDao.updateToken(
                tokenEntity = TokenEntity.from(tokenResult.data)
            )

        return@withContext tokenResult
    }

    suspend fun getTokenOrNull(): Token? = withContext(Dispatchers.IO) {
        tokenDao.getTokenOrNull()?.transform()
    }

    suspend fun clearToken() = withContext(Dispatchers.IO) {
        tokenDao.clearTokens()
    }
}
