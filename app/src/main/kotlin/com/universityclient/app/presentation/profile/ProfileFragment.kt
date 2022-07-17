package com.universityclient.app.presentation.profile

import androidx.fragment.app.viewModels
import com.universityclient.app.R
import com.universityclient.app.presentation.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels()
}
