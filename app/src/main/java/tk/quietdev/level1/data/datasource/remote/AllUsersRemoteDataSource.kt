package tk.quietdev.level1.data.datasource.remote

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.domain.models.UserModel

interface AllUsersRemoteDataSource {
    suspend fun getAllUsers(): Resource<List<UserModel>>
    suspend fun updateUser(userModel: UserModel): Resource<Boolean>
    suspend fun getUserContacts(): Resource<List<UserModel>>
    suspend fun getCurrentUser(): Resource<UserModel>

    suspend fun getCurrentUserId(): Resource<Int>
    suspend fun getCurrentUserContactsIdList(): Resource<List<Int>>
    suspend fun removeContact(contactId: Int): Resource<Boolean>
    suspend fun addContact(contactId: Int): Resource<Boolean>
    suspend fun editUser(user: UserModel): Resource<Boolean>

}