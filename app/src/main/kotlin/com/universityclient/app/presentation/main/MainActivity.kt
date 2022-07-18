package com.universityclient.app.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.universityclient.app.R
import com.universityclient.app.databinding.ActivityMainBinding
import com.universityclient.app.presentation.base.activity.BaseActivity
import com.universityclient.app.presentation.common.behavior.SnackBarBehavior
import com.universityclient.app.presentation.common.ext.lifecycleAware
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::bind)

    private val navHostFragment: NavHostFragment
        get() = binding.fcvNavHost.getFragment()

    private val navHostController: NavController
        get() = navHostFragment.navController

    private val snackBarBehavior by lifecycleAware {
        SnackBarBehavior(binding.layoutAlertSnackBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupCommands()
    }

    override fun onBackClicked() {
        val isEmptyBackStack = navHostController.previousBackStackEntry == null
        viewModel.onBackClicked(isEmptyBackStack)
    }

    private fun setupViews() {
        snackBarBehavior.setup()
    }

    var a = true

    private fun setupCommands() {
        viewModel.mainCommandHolder.showToastSharedFlow
            .onEach { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT)
                    .show()
            }
            .launchIn(lifecycleScope)

        with (viewModel.sharedMainCommandHolder) {

            showError.bind(lifecycleScope) { error ->
                val desc = if (a)
                    "Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc Long Desc "
                else
                    getString(error.descRes)

                a = !a

                snackBarBehavior.show {
                    it.textViewTitle.text = getString(error.titleRes)
                    it.textViewDesc.text = desc
                }
            }
        }
    }
}
