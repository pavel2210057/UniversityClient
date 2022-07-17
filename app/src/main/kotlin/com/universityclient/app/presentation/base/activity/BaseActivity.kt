package com.universityclient.app.presentation.base.activity

import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.universityclient.app.presentation.base.navigator.ActivityNavigationExecutor
import com.universityclient.app.presentation.base.viewModel.ActivityViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity :
    AppCompatActivity(),
    ActivityNavigationExecutor {

    protected abstract val viewModel: ActivityViewModel

    override fun onStart() {
        super.onStart()

        setupNavigation()
    }

    protected open fun onBackClicked() {
        viewModel.onDefaultBackClicked()
    }

    override fun onBackPressed() {
        onBackClicked()
    }

    @CallSuper
    protected open fun setupNavigation() {
        val navigationState = viewModel.navigationState

        navigationState.navigateUpSharedFlow
            .onEach {
                navigateUp()
            }
            .launchIn(lifecycleScope)

        navigationState.navigateSharedFlow
            .onEach { intent ->
                navigate(intent)
            }
            .launchIn(lifecycleScope)

        navigationState.finishSharedFlow
            .onEach {
                close()
            }
            .launchIn(lifecycleScope)
    }
}
