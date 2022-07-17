package com.universityclient.app.di

import com.moodle.client.MoodleClient
import com.moodle.client.component.SiteInfoComponent
import com.moodle.client.component.TokenComponent
import com.universityclient.app.BuildConfig
import com.universityclient.app.common.provider.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    fun provideMoodleClient(
        @Named("BaseUrl") baseUrl: String,
        tokenProvider: TokenProvider
    ): MoodleClient {
        return MoodleClient(
            domain = baseUrl,
            tokenProvider = tokenProvider
        )
    }

    @Provides
    fun provideMoodleTokenComponent(
        moodleClient: MoodleClient
    ): TokenComponent {
        return moodleClient.mainComponent.tokenComponent()
    }

    @Provides
    fun provideMoodleSiteInfoComponent(
        moodleClient: MoodleClient
    ): SiteInfoComponent {
        return moodleClient.mainComponent.siteInfoComponent()
    }
}
