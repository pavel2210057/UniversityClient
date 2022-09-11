package com.universityclient.app.di

import android.content.Context
import androidx.room.Room
import com.universityclient.app.data.db.MainDatabase
import com.universityclient.app.data.db.dao.ChatDao
import com.universityclient.app.data.db.dao.MetadataDao
import com.universityclient.app.data.db.dao.TokenDao
import com.universityclient.app.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMainDatabase(
        @ApplicationContext context: Context
    ): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            "MainDatabase"
        ).build()
    }

    @Provides
    fun provideTokenDao(
        mainDatabase: MainDatabase
    ): TokenDao {
        return mainDatabase.tokenDao()
    }

    @Provides
    fun provideMetadataDao(
        mainDatabase: MainDatabase
    ): MetadataDao {
        return mainDatabase.metadataDao()
    }

    @Provides
    fun provideUserDao(
        mainDatabase: MainDatabase
    ): UserDao {
        return mainDatabase.userDao()
    }

    @Provides
    fun provideChatDao(
        mainDatabase: MainDatabase
    ): ChatDao {
        return mainDatabase.chatDao()
    }
}
