package com.universityclient.app.presentation.profile

import androidx.lifecycle.viewModelScope
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.app.interactor.user.UserInteractor
import com.universityclient.app.presentation.base.viewModel.FragmentViewModel
import com.universityclient.app.presentation.common.UiResult
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
    private val userInteractor: UserInteractor
) : FragmentViewModel() {

    private val _sh = ProfileStateHolderFlowable(
        initialAccountListItemsState = emptyList(),
        initialUserUiState = UiResult.empty()
    )

    val sh = _sh.immutable()

    fun onScreenLoaded() {
        setupAccountItemList()
        loadUserData()
    }

    fun onAccountSingleTitleListItemClicked(item: AccountListItem.SingleTitleItem) {}

    private fun setupAccountItemList() {
        _sh.accountListItemsStateFlow.value = createAccountItemList()
    }

    private fun loadUserData() {
        _sh.userUiStateFlow.value = UiResult.loading()
        viewModelScope.launch {
            userInteractor.getAndLoadSelfUser()
                .onEach { result ->
                    when (result) {
                        is DataResult.Success -> {
                            _sh.userUiStateFlow.value = UiResult.success(result.data.asUserUi())
                        }
                        is DataResult.Failure -> {
                            if (_sh.userUiStateFlow.value.isEmpty())
                                //TODO handle exception with right cause
                                _sh.userUiStateFlow.value = UiResult.failure(result.cause)
                        }
                    }
                }
                .collect()
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
