package com.universityclient.app.presentation.base.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import me.flowable.core.Flowable
import me.flowable.core.Shared

@Flowable
data class FragmentNavigationCommandHolder(
    @Shared(replay = 1)
    val navigateUp: Unit,
    @Shared
    val navigate: NavDirections
)

abstract class FragmentViewModel : ViewModel() {

    private val _navigationCommandHolder = FragmentNavigationCommandHolderFlowable()
    val navigationState = _navigationCommandHolder.immutable()

    fun onDefaultBackClicked() {
        navigateUp()
    }

    protected fun navigateUp() {
        _navigationCommandHolder.navigateUpSharedFlow.tryEmit(Unit)
    }

    protected fun NavDirections.navigate() {
        _navigationCommandHolder.navigateSharedFlow.tryEmit(this)
    }
}
