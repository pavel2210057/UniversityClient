package com.universityclient.app.presentation.main

import com.universityclient.app.presentation.common.Command
import com.universityclient.app.presentation.common.asCommand
import com.universityclient.app.presentation.main.command.ShowError
import me.flowable.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Flowable
data class SharedMainCommands(
    val showError: ShowError
)

@Singleton
class SharedMainCommandHolder @Inject constructor() {
    private val commands = SharedMainCommandsFlowable()

    val showError: Command<ShowError>
        get() = commands.showErrorSharedFlow.asCommand()
}
