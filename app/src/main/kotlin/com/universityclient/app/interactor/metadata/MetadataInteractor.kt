package com.universityclient.app.interactor.metadata

import com.universityclient.app.data.repository.MetadataRepository
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.domain.SystemMetadata
import javax.inject.Inject

class MetadataInteractor @Inject constructor(
    private val metadataRepository: MetadataRepository
) {

    suspend fun loadAndSaveMetadata(): DataResult<SystemMetadata> {
        return metadataRepository.loadAndSaveMetadata()
    }

    suspend fun getOrLoadMetadata(): DataResult<SystemMetadata> {
        return metadataRepository.getOrLoadMetadata()
    }
}
