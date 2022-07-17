package com.universityclient.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.universityclient.app.data.db.dao.TokenDao
import com.universityclient.app.data.db.model.TokenEntity
import com.universityclient.app.data.db.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
        TokenEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao
}
