package com.universityclient.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.universityclient.app.data.db.constant.TableNames.ChatTable
import com.universityclient.app.data.db.model.ChatEntity

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chatEntity: ChatEntity)

    @Query("SELECT * FROM $ChatTable WHERE id=:id LIMIT 1")
    suspend fun getChatByIdOrNull(id: String): ChatEntity?

    @Query("SELECT * FROM $ChatTable")
    suspend fun getChats(): List<ChatEntity>

    @Query("DELETE FROM $ChatTable")
    suspend fun clearChats()
}
