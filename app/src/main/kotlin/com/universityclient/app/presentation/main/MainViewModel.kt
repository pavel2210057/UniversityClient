package com.universityclient.app.presentation.main

import androidx.lifecycle.viewModelScope
import com.universityclient.app.presentation.base.viewModel.ActivityViewModel
import com.universityclient.app.presentation.common.emitTo
import com.universityclient.app.presentation.main.command.ShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val sharedMainCommandHolder: SharedMainCommandHolder
) : ActivityViewModel() {

    private var closeTimerJob: Job? = null

    fun onBackClicked(isEmptyBackStack: Boolean) {
        if (!isEmptyBackStack) {
            navigateUp()
            return
        }

        val isJobActive = closeTimerJob?.isActive ?: false
        if (isJobActive) {
            closeScreen()
            return
        }

        closeTimerJob = viewModelScope.launch {
            ShowToast("Нажмите еще раз для закрытия") emitTo sharedMainCommandHolder.showToast
            delay(3000)
        }
    }

    override fun onCleared() {
        closeTimerJob?.cancel()
        closeTimerJob = null
    }
}
