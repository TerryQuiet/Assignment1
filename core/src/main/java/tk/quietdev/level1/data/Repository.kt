package tk.quietdev.level1.data

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel

interface Repository {

    fun userRegistration(login: String, password: String): Flow<Resource<UserModel?>>
    fun userLogin(login: String, password: String): Flow<Resource<UserModel?>>
    fun currentUserFlow(): Flow<Resource<UserModel?>>
    fun getAllUsersFlow(): Flow<Resource<List<UserModel>>>
    fun addUserContact(userModelId: Int): Flow<Resource<List<UserModel>>>
    fun updateUser(updatedUserModel: UserModel): Flow<Resource<UserModel?>>
    fun removeUserContact(userModelId: Int): Flow<Resource<List<UserModel>>>
    fun getCurrentUserContactIdsFlow(shouldFetch: Boolean = true): Flow<Resource<List<Int>>>
    fun getCurrentUserContactsFlow(
        list: List<Int>,
        shouldFetch: Boolean = true
    ): Flow<Resource<List<UserModel>>>

}