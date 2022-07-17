package com.moodle.client.component

import com.universityclient.domain.SystemInfo
import com.universityclient.domain.User

interface SiteInfoComponent {

    suspend fun getUser(): User

    suspend fun getSystemInfo(): SystemInfo

    suspend fun getUserAndSystemInfo(): Pair<User, SystemInfo>
}
