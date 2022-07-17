package com.universityclient.app.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.universityclient.app.R
import com.universityclient.app.presentation.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(layoutRes = R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onViewCreated()
    }
}
