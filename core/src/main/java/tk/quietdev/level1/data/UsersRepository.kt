package tk.quietdev.level1.data

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel

interface UsersRepository {
    fun flowUserById(id: Int): Flow<Resource<UserModel>>
    suspend fun refreshCurrentUser(): Resource<Boolean>
    suspend fun getCurrentUserId(): Resource<Int>
    suspend fun fetchCurrentUserId(): Resource<Int>
    suspend fun flowCurrentUserContactsIdList(): Flow<Resource<List<Int>>>
    suspend fun updateUser(userModel: UserModel, refreshCache: Boolean = true): Resource<Boolean>
    suspend fun getUsersByIdList(idList: List<Int>): List<UserModel>
    suspend fun refreshUserContacts(): Resource<Boolean>
    suspend fun refreshAllUsers(): Resource<Boolean>
    suspend fun getUsersByIdListExclude(idList: List<Int>): List<UserModel>
    suspend fun addUserContact(id: Int): Flow<Resource<Boolean>>
    suspend fun removeUserContact(id: Int): Flow<Resource<Boolean>>
}