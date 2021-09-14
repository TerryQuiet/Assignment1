package tk.quietdev.level1.repository

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.DataState
import tk.quietdev.level1.utils.Resource

interface Repository {
    fun updateUser(updatedUserModel: UserModel)
    fun addUser(userModel: UserModel): UserModel
    fun getUserWithNoValidation(id: Int): UserModel?
    fun getUserWithValidation(email: String, password: String): UserModel?
    fun getUserList(amount: Int = -1): List<UserModel>
    suspend fun userRegistration(login: String, password: String) : Flow<DataState<UserModel>>
    suspend fun userLogin(login: String, password: String) : Flow<DataState<UserModel>>
    fun currentUserFlow(): Flow<Resource<UserModel>>
    fun getCurrentUserContactsFlow(): Flow<Resource<List<UserModel>>>
    fun getAllUsersFlow(): Flow<Resource<List<UserModel>>>

}