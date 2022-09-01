package com.moodle.client.internal.di

import com.moodle.client.component.MetadataComponent
import com.moodle.client.component.TokenComponent
import com.moodle.client.component.UserComponent
import com.moodle.client.internal.component.MetadataComponentImpl
import com.moodle.client.internal.component.TokenComponentImpl
import com.moodle.client.internal.component.UserComponentImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ComponentModule {

    @Binds
    fun bindTokenComponent(tokenComponentImpl: TokenComponentImpl): TokenComponent

    @Binds
    fun bindMetadataComponent(metadataComponentImpl: MetadataComponentImpl): MetadataComponent

    @Binds
    fun bindUserComponent(userComponentImpl: UserComponentImpl): UserComponent
}
