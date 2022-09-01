package com.moodle.client.component

import com.moodle.client.result.ApiResult
import com.universityclient.domain.SystemMetadata

interface MetadataComponent {

    suspend fun getMetadata(): ApiResult<SystemMetadata>
}
