package com.universityclient.app.data.repository

import com.moodle.client.component.UserComponent
import com.universityclient.app.data.db.dao.UserDao
import com.universityclient.app.data.db.model.UserEntity
import com.universityclient.app.interactor.common.DataResult
import com.universityclient.app.interactor.common.asDataResult
import com.universityclient.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userComponent: UserComponent,
    private val userDao: UserDao,
    private val metadataRepository: MetadataRepository
) {

    suspend fun getCurrentUserId() = withContext(Dispatchers.IO) {
        metadataRepository.currentUserId()
    }

    fun getAndLoadSelfUser(): Flow<DataResult<User>> = flow {
        val userId = metadataRepository.currentUserId()

        if (userId == null)
            //TODO emit appropriate exception
            emit(DataResult.failure(Exception()))
        else
            emitAll(getAndLoadUser(userId))
    }

    suspend fun loadSelfUser(): DataResult<User> = withContext(Dispatchers.IO) {
        val userId = metadataRepository.currentUserId()

        if (userId == null)
            //TODO emit appropriate exception
            DataResult.failure(Exception())
        else
            loadUserById(userId)
    }

    suspend fun getCachedSelfUserOrNull(): User? = withContext(Dispatchers.IO) {
        val userId = metadataRepository.currentUserId()

        if (userId == null)
            null
        else
            getCachedUserByIdOrNull(userId)
    }

    fun getAndLoadUser(id: String): Flow<DataResult<User>> = flow {
        val cachedUser = getCachedUserByIdOrNull(id)
        if (cachedUser != null)
            emit(cachedUser.asDataResult())

        emit(loadUserById(id))
    }
        .flowOn(Dispatchers.IO)

    suspend fun loadUserById(id: String): DataResult<User> = withContext(Dispatchers.IO) {
        val user = userComponent.getUserById(id).asDataResult()

        if (user is DataResult.Success)
            userDao.insertUser(user.data.asUserEntity())

        user
    }

    suspend fun getCachedUserByIdOrNull(id: String): User? = withContext(Dispatchers.IO) {
        userDao.getUserByIdOrNull(id)?.transform()
    }

    private fun User.asUserEntity() = UserEntity(
        id = id,
        username = username,
        fullname = fullname,
        email = email,
        smallAvatarUrl = smallAvatarUrl,
        avatarUrl = avatarUrl,
        country = country,
        city = city,
        timeZone = timeZone,
        language = language,
        firstAccess = firstAccess,
        lastAccess = lastAccess,
        institution = institution,
        department = department
    )
}
