package com.moodle.client.internal.di

import com.moodle.client.component.SiteInfoComponent
import com.moodle.client.component.TokenComponent
import com.moodle.client.internal.component.SiteInfoComponentImpl
import com.moodle.client.internal.component.TokenComponentImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ComponentModule {

    @Binds
    fun bindTokenComponent(tokenComponentImpl: TokenComponentImpl): TokenComponent

    @Binds
    fun bindSiteInfoComponent(siteInfoComponentImpl: SiteInfoComponentImpl): SiteInfoComponent
}
