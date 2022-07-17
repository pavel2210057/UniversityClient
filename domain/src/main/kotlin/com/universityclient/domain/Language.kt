package com.universityclient.domain

enum class Language(val code: String) {
    Russian("ru"),
    English("en")
}

fun String.asLanguage() = when(this) {
    "ru" -> Language.Russian
    else -> Language.English
}
