package com.universityclient.app.data.db.dao

import androidx.room.*
import com.universityclient.app.data.db.constant.TableNames.TokenTable
import com.universityclient.app.data.db.model.TokenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(tokenEntity: TokenEntity)

    @Query("SELECT * FROM $TokenTable LIMIT 1")
    suspend fun getTokenOrNull(): TokenEntity?

    @Query("SELECT * FROM $TokenTable LIMIT 1")
    fun observeTokenChanges(): Flow<TokenEntity?>

    @Query("DELETE FROM $TokenTable")
    suspend fun clearTokens()

    @Transaction
    suspend fun updateToken(tokenEntity: TokenEntity) {
        clearTokens()
        insertToken(tokenEntity)
    }
}
