package com.universityclient.app.data.repository

import com.moodle.client.component.MetadataComponent
import com.moodle.client.result.ApiResult
import com.universityclient.app.data.db.dao.MetadataDao
import com.universityclient.app.data.db.model.MetadataEntity
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.domain.SystemMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MetadataRepository @Inject constructor(
    private val metadataComponent: MetadataComponent,
    private val metadataDao: MetadataDao
) {

    private var mutex = Mutex()
    private var currentUserId: String? = null

    suspend fun currentUserId(): String? = coroutineScope {
        mutex.withLock(currentUserId) {
            currentUserId ?: getCachedMetadataOrNull()?.userMetadata?.id?.also {
                currentUserId = it
            }
        }
    }

    suspend fun loadAndSaveMetadata(): DataResult<SystemMetadata> = withContext(Dispatchers.IO) {
        when (val result = metadataComponent.getMetadata()) {
            is ApiResult.Success -> {
                metadataDao.updateMetadata(result.data.asMetadataEntity())
                DataResult.success(result.data)
            }
            is ApiResult.Failure -> {
                DataResult.failure(result.cause)
            }
        }
    }

    suspend fun getOrLoadMetadata(): DataResult<SystemMetadata> = withContext(Dispatchers.IO) {
        val cached = getCachedMetadataOrNull()
        return@withContext if (cached != null)
            DataResult.success(cached)
        else
            loadAndSaveMetadata()
    }

    suspend fun getCachedMetadataOrNull(): SystemMetadata? = withContext(Dispatchers.IO) {
        metadataDao.getMetadataOrNull()?.transform()
    }

    private fun SystemMetadata.asMetadataEntity() = MetadataEntity(
        userId = userMetadata.id,
        canUserManageOwnFiles = userMetadata.canManageOwnFiles,
        isUserAdmin = userMetadata.isAdmin,
        siteId = siteId,
        siteName = siteName,
        siteUrl = siteUrl,
        language = language,
        maxUploadFileSize = maxUploadFileSize,
        themeName = themeName
    )
}
