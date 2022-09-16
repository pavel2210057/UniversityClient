package com.moodle.client.internal.component

import com.moodle.client.component.UserComponent
import com.moodle.client.internal.api.request.UsersByFieldRequest
import com.moodle.client.internal.api.service.RestService
import com.moodle.client.internal.component.ext.transformListOrFailure
import com.moodle.client.result.ApiResult
import com.universityclient.domain.User
import javax.inject.Inject

internal class UserComponentImpl @Inject constructor(
    private val restService: RestService
) : UserComponent {

    override suspend fun getUsersByField(
        field: UserComponent.Field,
        vararg values: String
    ): ApiResult<List<User>> {
        val request = UsersByFieldRequest(
            field = field.value,
            values = values.asList()
        )

        return restService.loadUsersByField(request).transformListOrFailure()
    }

    override suspend fun getUserById(id: String): ApiResult<User> {
        return when (val result = getUsersByField(UserComponent.Field.Id, id)) {
            is ApiResult.Success -> ApiResult.success(result.data.first())
            is ApiResult.Failure -> ApiResult.failure(result.cause)
        }
    }
}
