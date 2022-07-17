package com.moodle.client.result

sealed class ApiException(message: String) : RuntimeException(message) {

    abstract val key: String

    class InvalidLoginException(
        message: String,
        override val key: String = KEY
    ) : ApiException(message) {
        companion object {
            const val KEY = "invalidlogin"
        }
    }

    class InvalidTokenException(
        override val key: String = KEY,
        message: String
    ) : ApiException(message) {
        companion object {
            const val KEY = "invalidtoken"
        }
    }

    class ServiceUnavailableException(
        override val key: String = KEY,
        message: String
    ) : ApiException(message) {
        companion object {
            const val KEY = "servicenotavailable"
        }
    }

    class IncorrectFunctionException(
        override val key: String = KEY,
        message: String
    ) : ApiException(message) {
        companion object {
            const val KEY = "invalidrecord"
        }
    }

    class UnknownException(override val key: String, message: String) : ApiException(message)
}
