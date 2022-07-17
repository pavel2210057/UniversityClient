package com.moodle.client.result

sealed class Error<T>(throwable: Throwable) {

    class JsonParseError<T>(throwable: Throwable) : Error<T>(throwable)
}
