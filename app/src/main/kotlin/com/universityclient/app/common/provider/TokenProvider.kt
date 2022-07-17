package com.universityclient.app.common.provider

import com.moodle.client.TokenProvider
import com.universityclient.app.common.ext.cachedOrFirst
import com.universityclient.app.data.db.dao.TokenDao
import com.universityclient.domain.Token
import dagger.Reusable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@Reusable
class TokenProvider @Inject constructor(
    tokenDao: TokenDao
) : TokenProvider {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val tokenChangesFlow = tokenDao.observeTokenChanges()
        .shareIn(scope, SharingStarted.Eagerly, replay = 1)

    override suspend fun provideToken(): Token? = withContext(Dispatchers.IO) {
        tokenChangesFlow.cachedOrFirst()?.transform()
    }
}
