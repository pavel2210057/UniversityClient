package com.universityclient.app.presentation.main

import com.universityclient.app.presentation.common.Command
import com.universityclient.app.presentation.common.asCommand
import com.universityclient.app.presentation.main.command.ShowError
import com.universityclient.app.presentation.main.command.ShowToast
import me.flowable.core.Flowable
import me.flowable.core.Shared
import javax.inject.Inject
import javax.inject.Singleton

@Flowable
data class SharedMainCommands(
    @Shared(replay = 0) val showError: ShowError,
    @Shared(replay = 0) val showToast: ShowToast
)

@Singleton
class SharedMainCommandHolder @Inject constructor() {
    private val commands = SharedMainCommandsFlowable()

    val showError: Command<ShowError>
        get() = commands.showErrorSharedFlow.asCommand()

    val showToast: Command<ShowToast>
        get() = commands.showToastSharedFlow.asCommand()
}
