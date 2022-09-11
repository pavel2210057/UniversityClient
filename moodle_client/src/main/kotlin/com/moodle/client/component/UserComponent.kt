package com.moodle.client.component

import com.moodle.client.result.ApiResult
import com.universityclient.domain.User

interface UserComponent {

    suspend fun getUsersByField(field: Field, vararg values: String): ApiResult<List<User>>

    suspend fun getUserById(id: String): ApiResult<User>

    enum class Field(internal val value: String) {

        Id("id")
    }
}
