package com.universityclient.app.presentation.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.universityclient.app.R
import com.universityclient.app.databinding.FragmentAuthBinding
import com.universityclient.app.interactor.common.validation.ValidationState
import com.universityclient.app.presentation.base.fragment.BaseFragment
import com.universityclient.app.presentation.common.UiResult
import com.universityclient.app.presentation.common.view.enabled
import com.universityclient.app.presentation.common.view.loading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AuthFragment : BaseFragment(layoutRes = R.layout.fragment_auth) {

    override val viewModel: AuthViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentAuthBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupFlows()
    }

    private fun setupViews() {
        with (viewBinding) {
            buttonSubmit.setOnClickListener {
                viewModel.onAuthButtonClicked(
                    username = textInputViewLogin.text,
                    password = textInputViewPassword.text
                )
            }
        }
    }

    private fun setupFlows() {
        with(viewModel.sh) {
            authStateFlow
                .onEach { uiResult ->
                    if (uiResult is UiResult.Loading) {
                        viewBinding.buttonSubmit.loading()
                    } else if (uiResult is UiResult.Failure) {
                        viewBinding.buttonSubmit.enabled()
                    }
                }
                .launchIn(lifecycleScope)

            usernameValidationStateFlow
                .onEach { state ->
                    viewBinding.textInputViewLogin.error = when (state) {
                        is ValidationState.Empty, is ValidationState.Success -> ""
                        is ValidationState.Failure -> "Поле не заполнено"
                    }
                }
                .launchIn(lifecycleScope)

            passwordValidationStateFlow
                .onEach { state ->
                    viewBinding.textInputViewPassword.error = when (state) {
                        is ValidationState.Empty, is ValidationState.Success -> ""
                        is ValidationState.Failure -> "Поле не заполнено"
                    }
                }
                .launchIn(lifecycleScope)
        }
    }
}
