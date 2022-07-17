package com.universityclient.app.common.ext

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.takeWhile(count: Int, predicate: (T) -> Boolean) =
    takeWhile(predicate).take(count)

suspend fun <T> SharedFlow<T>.cachedOrFirst() =
    if (replayCache.isNotEmpty())
        replayCache.first()
    else
        first()
