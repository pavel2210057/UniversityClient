package com.universityclient.app.data.db.dao

import androidx.room.*
import com.universityclient.app.data.db.constant.TableNames.MetadataTable
import com.universityclient.app.data.db.model.MetadataEntity

@Dao
interface MetadataDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMetadata(metadataEntity: MetadataEntity)

    @Query("SELECT * FROM $MetadataTable LIMIT 1")
    suspend fun getMetadataOrNull(): MetadataEntity?

    @Query("DELETE FROM $MetadataTable")
    suspend fun clearMetadata()

    @Transaction
    suspend fun updateMetadata(metadataEntity: MetadataEntity) {
        clearMetadata()
        insertMetadata(metadataEntity)
    }
}
