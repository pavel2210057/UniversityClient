package com.universityclient.app.presentation.splash

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.universityclient.app.data.repository.TokenRepository
import com.universityclient.app.presentation.base.viewModel.FragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : FragmentViewModel() {

    fun onViewCreated() {
        viewModelScope.launch {
            val timerJob = launch { delay(MIN_LOAD_TIME) }
            val startScreenDirection = defineStartScreen()

            timerJob.join()

            startScreenDirection.navigate()
        }
    }

    private suspend fun defineStartScreen(): NavDirections {
        val isTokenExists = tokenRepository.getTokenOrNull() != null

        return if (!isTokenExists)
            SplashFragmentDirections.splashToAuth()
        else
            SplashFragmentDirections.splashToHub()
    }

    companion object {
        private const val MIN_LOAD_TIME = 2000L
    }
}
