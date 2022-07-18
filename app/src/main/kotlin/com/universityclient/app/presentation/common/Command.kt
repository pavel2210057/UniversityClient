package com.universityclient.app.presentation.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface CommandUnit

abstract class Command<C : CommandUnit> {

    abstract fun bind(scope: CoroutineScope, block: suspend (C) -> Unit): Job

    abstract suspend fun emit(value: C)

    abstract fun tryEmit(value: C)
}

fun<C : CommandUnit> MutableSharedFlow<C>.asCommand() = object : Command<C>() {

    override fun bind(scope: CoroutineScope, block: suspend (C) -> Unit): Job {
        return onEach(block).launchIn(scope)
    }

    override suspend fun emit(value: C) { this@asCommand.emit(value) }

    override fun tryEmit(value: C) { this@asCommand.tryEmit(value) }
}

suspend infix fun<C : CommandUnit> C.emitTo(command: Command<C>) { command.emit(this) }
