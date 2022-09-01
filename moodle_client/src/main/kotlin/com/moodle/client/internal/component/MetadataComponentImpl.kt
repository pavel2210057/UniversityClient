package com.moodle.client.internal.component

import com.moodle.client.component.MetadataComponent
import com.moodle.client.internal.api.service.RestService
import com.moodle.client.internal.component.ext.transformOrFailure
import com.moodle.client.result.ApiResult
import com.universityclient.domain.SystemMetadata
import javax.inject.Inject

internal class MetadataComponentImpl @Inject constructor(
    private val restService: RestService
) : MetadataComponent {

    override suspend fun getMetadata(): ApiResult<SystemMetadata> {
        return restService.loadMetadata().transformOrFailure()
    }
}
