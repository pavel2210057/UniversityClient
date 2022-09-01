package com.universityclient.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.universityclient.app.data.db.dao.MetadataDao
import com.universityclient.app.data.db.dao.TokenDao
import com.universityclient.app.data.db.dao.UserDao
import com.universityclient.app.data.db.model.MetadataEntity
import com.universityclient.app.data.db.model.TokenEntity
import com.universityclient.app.data.db.model.UserEntity

@Database(
    entities = [
        TokenEntity::class,
        MetadataEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao

    abstract fun metadataDao(): MetadataDao

    abstract fun userDao(): UserDao
}
