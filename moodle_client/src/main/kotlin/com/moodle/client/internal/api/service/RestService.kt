package com.moodle.client.internal.api.service

import com.moodle.client.internal.api.asApiResult
import com.moodle.client.internal.api.constant.Functions
import com.moodle.client.internal.api.constant.RestUrl
import com.moodle.client.internal.api.request.UsersByFieldRequest
import com.moodle.client.internal.api.requestModifier.MobileServiceRequestModifier
import com.moodle.client.internal.api.requestModifier.modifyWith
import com.moodle.client.internal.api.response.MetadataResponse
import com.moodle.client.internal.api.response.UserResponse
import com.moodle.client.result.ApiResult
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*

internal class RestService (
    private val client: HttpClient
) {

    suspend fun loadMetadata(): ApiResult<MetadataResponse> {
        val response = client.get(RestUrl.REST) {
            modifyWith(MobileServiceRequestModifier) {
                this.functionName = Functions.GET_METADATA
            }
        }

        return response.asApiResult()
    }

    suspend fun loadUsersByField(usersByFieldRequest: UsersByFieldRequest): ApiResult<List<UserResponse>> {
        val response = client.get(usersByFieldRequest) {
            modifyWith(MobileServiceRequestModifier) {
                this.functionName = Functions.GET_USERS_BY_FIELD
            }
        }

        return response.asApiResult()
    }
}
