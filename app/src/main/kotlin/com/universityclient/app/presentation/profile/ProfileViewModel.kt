package com.universityclient.app.presentation.profile

import androidx.lifecycle.viewModelScope
import com.universityclient.app.R
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.app.interactor.user.UserInteractor
import com.universityclient.app.presentation.base.viewModel.FragmentViewModel
import com.universityclient.app.presentation.common.UiResult
import com.universityclient.app.presentation.main.SharedMainCommandHolder
import com.universityclient.app.presentation.main.command.ShowError
import com.universityclient.app.presentation.profile.adapter.AccountListItem
import com.universityclient.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.flowable.core.Flowable
import me.flowable.core.State
import javax.inject.Inject

@Flowable
data class ProfileStateHolder(
    @State val accountListItems: List<AccountListItem>,
    @State val userUi: UiResult<UserUi>
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val sharedCommandHolder: SharedMainCommandHolder
) : FragmentViewModel() {

    private val _sh = ProfileStateHolderFlowable(
        initialAccountListItemsState = emptyList(),
        initialUserUiState = UiResult.empty()
    )

    val sh = _sh.immutable()

    fun onScreenLoaded() {
        setupAccountItemList()
        setupUserData()
    }

    fun onScreenRefresh() {
        loadUserData()
    }

    fun onAccountSingleTitleListItemClicked(item: AccountListItem.SingleTitleItem) {}

    private fun setupAccountItemList() {
        _sh.accountListItemsStateFlow.value = createAccountItemList()
    }

    private fun setupUserData() {
        _sh.userUiStateFlow.value = UiResult.loading()
        viewModelScope.launch {
            userInteractor.getAndLoadSelfUser()
                .onEach { result ->
                    when {
                        result is DataResult.Success -> {
                            _sh.userUiStateFlow.value = UiResult.success(result.data.asUserUi())
                        }
                        result is DataResult.Failure && _sh.userUiStateFlow.value.isEmpty() -> {
                            //TODO handle exception with right cause
                            sharedCommandHolder.showError.emit(
                                ShowError(
                                    titleRes = R.string.authScreen_invalidData_error_title,
                                    descRes = R.string.authScreen_error_desc
                                )
                            )
                            _sh.userUiStateFlow.value = UiResult.failure(result.cause)
                        }
                    }
                }
                .collect()
        }
    }

    private fun loadUserData() {
        _sh.userUiStateFlow.value = UiResult.loading()
        viewModelScope.launch {
            _sh.userUiStateFlow.value = when (val result = userInteractor.loadSelfUser()) {
                is DataResult.Success -> UiResult.success(result.data.asUserUi())
                is DataResult.Failure -> UiResult.failure(result.cause)
            }
        }
    }

    private fun createAccountItemList() = listOf(
        AccountListItem.SingleTitleItem.MyGroup,
        AccountListItem.SingleTitleItem.MyCourses,
        AccountListItem.SingleTitleItem.MyDebts,
        AccountListItem.SingleTitleItem.Settings,
    )

    private fun User.asUserUi() = UserUi(
        fullName = fullname,
        city = city,
        smallAvatarUrl = smallAvatarUrl,
        avatarUrl = avatarUrl
    )
}
