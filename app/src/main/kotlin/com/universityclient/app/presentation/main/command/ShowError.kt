package com.universityclient.app.presentation.main.command

import androidx.annotation.StringRes
import com.universityclient.app.presentation.common.CommandUnit

data class ShowError(
    @StringRes val titleRes: Int,
    @StringRes val descRes: Int
) : CommandUnit
