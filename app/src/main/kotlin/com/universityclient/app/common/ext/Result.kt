package com.universityclient.app.common.ext

fun<T> Result<T>.throwable() = exceptionOrNull()!!

fun<T> Result<T>.exception() = throwable() as Exception
