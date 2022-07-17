package com.universityclient.app.presentation.main.di

import com.universityclient.app.presentation.common.Command
import com.universityclient.app.presentation.common.asCommand
import com.universityclient.app.presentation.main.SharedMainCommandHolderFlowable
import com.universityclient.app.presentation.main.SharedMainCommandHolderFlowableImpl
import com.universityclient.app.presentation.main.command.ShowError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MainActivityModule {

    @Singleton
    @Provides
    fun provideSharedMainCommandHolder(): SharedMainCommandHolderFlowableImpl {
        return SharedMainCommandHolderFlowable()
    }

    @Singleton
    @Provides
    fun provideShowErrorCommand(ch: SharedMainCommandHolderFlowableImpl): Command<ShowError> {
        return ch.showErrorSharedFlow.asCommand()
    }
}
