package com.universityclient.app.interactor.user

import com.universityclient.app.data.repository.UserRepository
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.domain.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun getAndLoadSelfUser(): Flow<DataResult<User>> {
        return userRepository.getAndLoadSelfUser()
    }

    suspend fun loadSelfUser(): DataResult<User> {
        return userRepository.loadSelfUser()
    }

    suspend fun getCachedSelfUserOrNull(): User? {
        return userRepository.getCachedSelfUserOrNull()
    }
}
