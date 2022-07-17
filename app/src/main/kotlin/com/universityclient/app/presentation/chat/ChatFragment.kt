package com.universityclient.app.presentation.chat

import androidx.fragment.app.viewModels
import com.universityclient.app.R
import com.universityclient.app.presentation.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment(R.layout.fragment_chat) {

    override val viewModel: ChatViewModel by viewModels()
}