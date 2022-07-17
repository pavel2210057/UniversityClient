package com.moodle.client

import com.moodle.client.component.MainComponent
import com.moodle.client.internal.di.DaggerMoodleComponent

class MoodleClient(
    domain: String,
    tokenProvider: TokenProvider
) {

    private val component = DaggerMoodleComponent.builder()
        .domain(domain)
        .tokenProvider(tokenProvider)
        .build()

    val mainComponent: MainComponent = component
}
