package com.moodle.client.internal.di

import com.moodle.client.internal.annotation.Domain
import com.moodle.client.internal.api.requestModifier.FormatRequestModifier
import com.moodle.client.internal.api.requestModifier.TokenRequestModifier
import com.moodle.client.internal.api.requestModifier.interceptors
import com.moodle.client.internal.api.service.RestService
import com.moodle.client.internal.api.service.TokenApiService
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*

@Module
internal class MoodleModule {

    @Provides
    fun provideKtor(
        @Domain domain: String,
        tokenRequestModifier: TokenRequestModifier,
        formatRequestModifier: FormatRequestModifier
    ): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                url(domain)
            }

            install(Logging)
            install(HttpSend)
        }.apply {
            plugin(HttpSend).interceptors(
                tokenRequestModifier,
                formatRequestModifier
            )
        }
    }

    @Provides
    fun provideTokenService(httpClient: HttpClient): TokenApiService {
        return TokenApiService(httpClient)
    }

    @Provides
    fun provideRestService(httpClient: HttpClient): RestService {
        return RestService(httpClient)
    }
}
