package com.universityclient.app.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.universityclient.app.R
import com.universityclient.app.databinding.FragmentProfileBinding
import com.universityclient.app.presentation.base.fragment.BaseFragment
import com.universityclient.app.presentation.common.UiResult
import com.universityclient.app.presentation.common.ext.dp
import com.universityclient.app.presentation.common.ext.listAdapterDelegates
import com.universityclient.app.presentation.common.recyclerView.ItemDecorationDelegates
import com.universityclient.app.presentation.common.recyclerView.OffsetItemDecorationDelegate
import com.universityclient.app.presentation.common.recyclerView.addItemDecoration
import com.universityclient.app.presentation.profile.adapter.AccountListItem
import com.universityclient.app.presentation.profile.adapter.accountSingleTitleListItemDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels()

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val accountListItemAdapter = listAdapterDelegates(
        accountSingleTitleListItemDelegate(
            onClicked =  { viewModel.onAccountSingleTitleListItemClicked(it) }
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupFlows()

        viewModel.onScreenLoaded()
    }

    private fun setupViews() {
        binding.root.apply {
            setOnChildScrollUpCallback { _, _ ->
                binding.motionLayout.currentState != binding.motionLayout.startState
            }

            setOnRefreshListener {
                viewModel.onScreenRefresh()
            }
        }

        binding.toolbar.setOnClickListener {
            binding.motionLayout.transitionToStart()
        }

        binding.recyclerViewAccountList.apply {
            adapter = accountListItemAdapter
            layoutManager = LinearLayoutManager(context)

            addAccountItemListDecoration()
        }
    }

    private fun setupFlows() {
        with (viewModel.sh) {
            accountListItemsStateFlow
                .onEach(::inflateAccountList)
                .launchIn(lifecycleScope)

            userUiStateFlow
                .onEach(::handleUserResult)
                .launchIn(lifecycleScope)
        }
    }

    private fun RecyclerView.addAccountItemListDecoration() {
        val offsetDecorationDelegate = OffsetItemDecorationDelegate(
            middle = 18.dp().toInt(),
            end = 18.dp().toInt()
        )

        val decoration = ItemDecorationDelegates.Builder<AccountListItem>()
            .addItemDecoration<AccountListItem.SingleTitleItem, AccountListItem>(
                offsetDecorationDelegate
            )
            .build()

        addItemDecoration(decoration)
    }

    private fun inflateAccountList(data: List<AccountListItem>) {
        accountListItemAdapter.items = data
    }

    private fun handleUserResult(uiUserMetadata: UiResult<UserUi>) {
        when (uiUserMetadata) {
            is UiResult.Success -> fillUserFields(uiUserMetadata.data)
        }
    }

    private fun fillUserFields(userUi: UserUi) {
        binding.root.isRefreshing = false

        with(binding) {
            Glide.with(this@ProfileFragment)
                .load(userUi.avatarUrl)
                .circleCrop()
                .into(imageViewAvatar)

            Glide.with(this@ProfileFragment)
                .load(userUi.smallAvatarUrl)
                .circleCrop()
                .into(imageViewToolbarAvatar)

            toolbar.title = userUi.fullName
            textViewName.text = userUi.fullName

            textViewCity.text = userUi.city
        }
    }
}
