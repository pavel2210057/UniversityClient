package com.moodle.client.internal.component

import com.moodle.client.component.SiteInfoComponent
import com.universityclient.domain.SystemInfo
import com.universityclient.domain.User
import javax.inject.Inject

internal class SiteInfoComponentImpl @Inject constructor(
//    private val siteInfoService: SiteInfoService
) : SiteInfoComponent {

    override suspend fun getUser(): User {
        TODO()
//        return siteInfoService.getSiteInfo().transformAsUser()
    }

    override suspend fun getSystemInfo(): SystemInfo {
        TODO()
//        return siteInfoService.getSiteInfo().transformAsSystemInfo()
    }

    override suspend fun getUserAndSystemInfo(): Pair<User, SystemInfo> {
        TODO()
//        val model = siteInfoService.getSiteInfo()
//        return model.transformAsUser() to model.transformAsSystemInfo()
    }
}
