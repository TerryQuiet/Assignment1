package tk.quietdev.level1.data

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel

interface AllUsersRepository {
    fun flowUserById(id: Int): Flow<Resource<UserModel>>
    suspend fun refreshCurrentUser(): Resource<Boolean>
    suspend fun getCurrentUserId(): Resource<Int>
    suspend fun fetchCurrentUserId(): Resource<Int>
    suspend fun flowCurrentUserContactsIdList(): Flow<Resource<List<Int>>>
    suspend fun updateCurrentUserContactIdList()
}