package tk.quietdev.level1.repository

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Resource

interface Repository {

    fun addUser(userModel: UserModel): UserModel
    fun getUserWithNoValidation(id: Int): UserModel?
    fun getUserWithValidation(email: String, password: String): UserModel?
    fun getUserList(amount: Int = -1): List<UserModel>
    fun userRegistration(login: String, password: String): Flow<Resource<UserModel>>
    fun userLogin(login: String, password: String): Flow<Resource<UserModel>>
    fun currentUserFlow(): Flow<Resource<UserModel>>
    fun getAllUsersFlow(): Flow<Resource<List<UserModel>>>
    fun addUserContact(userModel: UserModel): Flow<Resource<List<UserModel>>>
    fun updateUser(updatedUserModel: UserModel): Flow<Resource<UserModel>>
    fun removeUserContact(userModel: UserModel): Flow<Resource<List<UserModel>>>
}