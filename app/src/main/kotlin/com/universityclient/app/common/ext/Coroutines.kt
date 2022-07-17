package com.universityclient.app.common.ext

import kotlinx.coroutines.*

/**
 * launches a coroutine with minimal execute time
 */
fun CoroutineScope.launch(atLeastTime: Long, block: suspend () -> Unit): Job {
    return launch {
        val timerJob = async { delay(atLeastTime) }
        block()
        timerJob.join()
    }
}

/**
 * execute block of code with minimal execute time
 */
suspend fun<T> executeAtLeast(atLeastTime: Long, block: suspend () -> T) = coroutineScope {
    val timerJob = async { delay(atLeastTime) }
    val result = block()
    timerJob.join()

    return@coroutineScope result
}
