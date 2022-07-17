package com.moodle.client.component

interface MainComponent {

    fun tokenComponent(): TokenComponent

    fun siteInfoComponent(): SiteInfoComponent
}
