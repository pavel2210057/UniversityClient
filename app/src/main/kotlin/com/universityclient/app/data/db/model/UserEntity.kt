package com.universityclient.app.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.universityclient.app.data.db.constant.TableNames

@Entity(
    tableName = TableNames.UserTable
)
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val avatarUrl: String
)
