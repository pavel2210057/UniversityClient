package com.universityclient.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.universityclient.app.data.db.constant.TableNames.UserTable
import com.universityclient.app.data.db.model.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM $UserTable WHERE id=:id LIMIT 1")
    suspend fun getUserByIdOrNull(id: String): UserEntity?

    @Query("DELETE FROM $UserTable")
    suspend fun clearUsers()
}
