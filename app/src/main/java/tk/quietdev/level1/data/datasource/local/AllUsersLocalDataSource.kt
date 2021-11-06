package tk.quietdev.level1.data.datasource.local

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel

interface AllUsersLocalDataSource {
    fun flowCurrentUserContactsIdList(): Flow<Resource<List<Int>>>
    fun getUserFlow(id: Int): Flow<Resource<UserModel>>

    suspend fun removeContacts(toRemove: List<Int>): Resource<Boolean>
    suspend fun addContact(id: Int): Resource<Boolean>
    suspend fun getUsersByIdListExclude(excludeList: List<Int>): List<UserModel>
    suspend fun putUser(userModel: UserModel): Resource<Boolean>
    suspend fun refreshUserContactList(userList: List<UserModel>): Resource<Boolean>
    suspend fun getCurrentUserId(): Resource<Int>
    suspend fun setCurrentUserId(userId: Int)
    suspend fun getUsersByIdList(idList: List<Int>): List<UserModel>
    suspend fun refreshAllUsersList(userList: List<UserModel>): Resource<Boolean>
}