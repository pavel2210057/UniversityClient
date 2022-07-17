package com.moodle.client.internal.api.requestModifier

import io.ktor.client.request.*

internal class MobileServiceRequestModifier(
    private val config: Config
) : RequestModifier<MobileServiceRequestModifier.Config> {

    override suspend fun modify(httpRequestBuilder: HttpRequestBuilder): HttpRequestBuilder {
        return httpRequestBuilder.apply {
            if (config.functionName.isNotEmpty())
                parameter(FUNCTION_PARAMETER_NAME, config.functionName)

            if (config.needMobileService)
                parameter(MOBILE_SERVICE_PARAMETER_NAME, MOBILE_SERVICE_PARAMETER_VALUE)
        }
    }

    data class Config(
        var functionName: String,
        var needMobileService: Boolean
    )

    companion object : RequestModifierBuilder<Config, MobileServiceRequestModifier> {

        override fun build(block: Config.() -> Unit): MobileServiceRequestModifier {
            val config = makeDefaultConfig()
            config.block()
            return MobileServiceRequestModifier(config)
        }

        private fun makeDefaultConfig() = Config(
            functionName = "",
            needMobileService = false
        )

        private const val MOBILE_SERVICE_PARAMETER_NAME = "service"
        private const val MOBILE_SERVICE_PARAMETER_VALUE = "moodle_mobile_app"
        private const val FUNCTION_PARAMETER_NAME = "wsfunction"
    }
}
