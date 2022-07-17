package com.universityclient.app.presentation.hub

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.universityclient.app.R
import com.universityclient.app.databinding.FragmentHubBinding
import com.universityclient.app.presentation.base.fragment.BaseFragment
import com.universityclient.app.presentation.common.behavior.TabBehavior
import com.universityclient.app.presentation.hub.pager.HubPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HubFragment : BaseFragment(R.layout.fragment_hub) {

    override val viewModel: HubViewModel by viewModels()

    private val binding by viewBinding(FragmentHubBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        binding.viewPagerHub.adapter = HubPagerAdapter(this)

        TabBehavior(
            binding.textViewTabHome,
            binding.textViewTabChat,
            binding.textViewTabProfile
        ).apply {
            reactOnViewPager(binding.viewPagerHub)
            addTabChangedCallback { _, position ->
                binding.viewPagerHub.currentItem = position
            }
        }
    }
}
