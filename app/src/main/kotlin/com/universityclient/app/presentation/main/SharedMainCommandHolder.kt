package com.universityclient.app.presentation.main

import com.universityclient.app.presentation.main.command.ShowError
import me.flowable.core.Flowable

@Flowable
data class SharedMainCommandHolder(
    val showError: ShowError
)
