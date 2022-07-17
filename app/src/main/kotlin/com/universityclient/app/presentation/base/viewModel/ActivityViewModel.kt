package com.universityclient.app.presentation.base.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import me.flowable.core.Flowable
import me.flowable.core.Shared

@Flowable
data class ActivityNavigationCommandHolder(
    @Shared(replay = 1)
    val navigateUp: Unit,
    @Shared
    val navigate: Intent,
    @Shared
    val finish: Unit
)

abstract class ActivityViewModel : ViewModel() {

    private val _navigationCommandHolder = ActivityNavigationCommandHolderFlowable()
    val navigationState = _navigationCommandHolder.immutable()

    fun onDefaultBackClicked() {
        navigateUp()
    }

    protected fun navigateUp() {
        _navigationCommandHolder.navigateUpSharedFlow.tryEmit(Unit)
    }

    protected fun Intent.navigate() {
        _navigationCommandHolder.navigateSharedFlow.tryEmit(this)
    }

    protected fun closeScreen() {
        _navigationCommandHolder.finishSharedFlow.tryEmit(Unit)
    }
}
