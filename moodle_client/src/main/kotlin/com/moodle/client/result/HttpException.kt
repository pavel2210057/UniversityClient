package com.moodle.client.result

sealed class HttpException<T>(code: Int, message: String)
