package com.universityclient.app.presentation.auth

import androidx.lifecycle.viewModelScope
import com.moodle.client.result.ApiException
import com.universityclient.app.R
import com.universityclient.app.common.ext.exception
import com.universityclient.app.interactor.auth.AuthInteractor
import com.universityclient.app.interactor.common.validation.*
import com.universityclient.app.presentation.base.viewModel.FragmentViewModel
import com.universityclient.app.presentation.common.UiResult
import com.universityclient.app.presentation.main.SharedMainCommandHolder
import com.universityclient.app.presentation.main.command.ShowError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.flowable.core.Flowable
import me.flowable.core.State
import javax.inject.Inject

@Flowable
data class AuthStateHolder(
    @State
    val auth: UiResult<Unit, Exception>,
    @State
    val usernameValidation: ValidationState,
    @State
    val passwordValidation: ValidationState
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val sharedMainCommandHolder: SharedMainCommandHolder
) : FragmentViewModel() {

    private val _sh = AuthStateHolderFlowable(
        initialAuthState = UiResult.empty(),
        initialUsernameValidationState = ValidationState.empty(),
        initialPasswordValidationState = ValidationState.empty(),
    )

    val sh = _sh.immutable()

    fun onAuthButtonClicked(username: String, password: String) {
        _sh.authStateFlow.value = UiResult.loading()
        viewModelScope.launch {
            val result = authInteractor.login(
                usernameSubject = username validateTo _sh.usernameValidationStateFlow,
                passwordSubject = password validateTo _sh.passwordValidationStateFlow
            )

            if (result.isFailure) {
                val e = result.exception()

                if (e is ApiException.InvalidLoginException)
                    sharedMainCommandHolder.showError.emit(
                        ShowError(
                            titleRes = R.string.authScreen_invalidData_error_title,
                            descRes = R.string.authScreen_invalidData_error_desc
                        )
                    )

                _sh.authStateFlow.value = UiResult.failure(e)
            } else {
                AuthFragmentDirections.authToHub().navigate()
            }
        }
    }
}
