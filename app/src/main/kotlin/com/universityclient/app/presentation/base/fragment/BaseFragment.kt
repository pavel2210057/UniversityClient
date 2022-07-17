package com.universityclient.app.presentation.base.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.universityclient.app.presentation.base.navigator.FragmentNavigationExecutor
import com.universityclient.app.presentation.base.viewModel.FragmentViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment :
    Fragment,
    FragmentNavigationExecutor {

    protected abstract val viewModel: FragmentViewModel

    constructor() : super()

    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBackClicked()
        setupNavigation()
    }

    protected open fun OnBackPressedCallback.onBackClicked() {
        viewModel.onDefaultBackClicked()
    }

    private fun setupBackClicked() {
        requireActivity().onBackPressedDispatcher.addCallback(owner = this) { onBackClicked() }
    }

    private fun setupNavigation() {
        val navigationState = viewModel.navigationState
        navigationState.navigateUpSharedFlow
            .onEach {
                navigateUp()
            }
            .launchIn(lifecycleScope)

        navigationState.navigateSharedFlow
            .onEach { navDirection ->
                navigate(navDirection)
            }
            .launchIn(lifecycleScope)
    }
}
