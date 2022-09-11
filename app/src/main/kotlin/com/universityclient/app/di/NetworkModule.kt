package com.universityclient.app.di

import com.moodle.client.MoodleClient
import com.moodle.client.component.ChatComponent
import com.moodle.client.component.MetadataComponent
import com.moodle.client.component.TokenComponent
import com.moodle.client.component.UserComponent
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
    fun provideMoodleMetadataComponent(
        moodleClient: MoodleClient
    ): MetadataComponent {
        return moodleClient.mainComponent.metadataComponent()
    }

    @Provides
    fun provideMoodleUserComponent(
        moodleClient: MoodleClient
    ): UserComponent {
        return moodleClient.mainComponent.userComponent()
    }

    @Provides
    fun provideMoodleChatComponent(
        moodleClient: MoodleClient
    ): ChatComponent {
        return moodleClient.mainComponent.chatComponent()
    }
}
