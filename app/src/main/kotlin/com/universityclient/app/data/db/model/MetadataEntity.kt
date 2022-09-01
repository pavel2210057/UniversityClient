package com.universityclient.app.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.universityclient.app.data.db.constant.TableNames
import com.universityclient.domain.Language
import com.universityclient.domain.SystemMetadata

@Entity(
    tableName = TableNames.MetadataTable
)
data class MetadataEntity(
    val userId: String,
    val canUserManageOwnFiles: Boolean,
    val isUserAdmin: Boolean,
    val siteId: String,
    val siteName: String,
    val siteUrl: String,
    val language: Language,
    val maxUploadFileSize: Long,
    val themeName: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun transform() = SystemMetadata(
        userMetadata = SystemMetadata.UserMetadata(
            id = userId,
            canManageOwnFiles = canUserManageOwnFiles,
            isAdmin = isUserAdmin
        ),
        siteId = siteId,
        siteName = siteName,
        siteUrl = siteUrl,
        language = language,
        maxUploadFileSize = maxUploadFileSize,
        themeName = themeName
    )
}
