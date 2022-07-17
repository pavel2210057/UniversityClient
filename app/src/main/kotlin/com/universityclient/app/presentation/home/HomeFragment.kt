package com.universityclient.app.presentation.home

import androidx.fragment.app.viewModels
import com.universityclient.app.R
import com.universityclient.app.presentation.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
}
