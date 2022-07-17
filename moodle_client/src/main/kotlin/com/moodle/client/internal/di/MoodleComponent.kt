package com.moodle.client.internal.di

import com.moodle.client.TokenProvider
import com.moodle.client.component.MainComponent
import com.moodle.client.internal.annotation.Domain
import dagger.BindsInstance
import dagger.Component

@Component(modules = [MoodleModule::class, ComponentModule::class])
internal interface MoodleComponent : MainComponent {

    @Component.Builder
    interface Builder {
        fun domain(@BindsInstance @Domain domain: String): Builder
        fun tokenProvider(@BindsInstance tokenProvider: TokenProvider): Builder
        fun build(): MoodleComponent
    }
}
