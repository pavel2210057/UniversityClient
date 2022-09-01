package com.moodle.client.internal.component.ext

import com.moodle.client.internal.api.Transformable
import com.moodle.client.result.ApiResult

fun<F : Transformable<T>, T> ApiResult<F>.transformOrFailure(): ApiResult<T> = when(this) {
    is ApiResult.Success -> ApiResult.success(data.transform())
    is ApiResult.Failure -> ApiResult.failure(cause)
}
