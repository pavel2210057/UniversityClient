package com.universityclient.app.presentation.main

import androidx.lifecycle.viewModelScope
import com.universityclient.app.presentation.base.viewModel.ActivityViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.flowable.core.Flowable
import javax.inject.Inject

@Flowable
data class MainCommandHolder(
    val showToast: String
)

@HiltViewModel
class MainViewModel @Inject constructor(
    val sharedMainCommandHolder: SharedMainCommandHolder
) : ActivityViewModel() {

    private val _mainCommandHolder = MainCommandHolderFlowable()
    val mainCommandHolder = _mainCommandHolder.immutable()

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
            _mainCommandHolder.showToastSharedFlow.tryEmit("Нажмите еще раз для закрытия")
            delay(3000)
        }
    }

    override fun onCleared() {
        closeTimerJob?.cancel()
        closeTimerJob = null
    }
}
