package com.universityclient.app.di

import android.content.Context
import androidx.room.Room
import com.universityclient.app.data.db.MainDatabase
import com.universityclient.app.data.db.dao.TokenDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

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
}
