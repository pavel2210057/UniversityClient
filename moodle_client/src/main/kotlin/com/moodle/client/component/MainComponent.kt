package com.moodle.client.component

interface MainComponent {

    fun tokenComponent(): TokenComponent

    fun metadataComponent(): MetadataComponent

    fun userComponent(): UserComponent
}
