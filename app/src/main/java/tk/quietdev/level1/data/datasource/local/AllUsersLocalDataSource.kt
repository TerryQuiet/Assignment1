package tk.quietdev.level1.data.datasource.local

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel

interface AllUsersLocalDataSource {
    fun getAllUsersFlow(): Flow<Resource<List<UserModel>>>
    fun getUserFlow(id: Int): Flow<Resource<UserModel>>
    fun getIncludeUsersFlow(includeList: List<Int>): Flow<Resource<List<UserModel>>>
    fun getExcludeUsersFlow(excludeList: List<Int>): Flow<Resource<List<UserModel>>>
    suspend fun putUser(userModel: UserModel): Resource<Boolean>
    suspend fun setUserList(userList: List<UserModel>): Resource<Boolean>

    suspend fun getCurrentUserId(): Resource<Int>
    suspend fun getCurrentUserContactsIdList(): Resource<List<Int>>
    suspend fun setCurrentUserId(userId: Int)
    suspend fun setCurrentUserContactsIdList(idList: List<Int>)
    suspend fun flowCurrentUserContactsIdList(): Flow<Resource<List<Int>>>
}