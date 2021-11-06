package tk.quietdev.level1.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.UsersRepository
import tk.quietdev.level1.data.datasource.local.AllUsersLocalDataSource
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSource
import tk.quietdev.level1.domain.models.UserModel

class UsersRepositoryImpl(
    private val allUsersRemoteDataSource: AllUsersRemoteDataSource,
    private val allUsersLocalDataSource: AllUsersLocalDataSource
) : UsersRepository {
    override fun flowUserById(id: Int): Flow<Resource<UserModel>> =
        allUsersLocalDataSource.getUserFlow(id = id)

    override suspend fun refreshCurrentUser(): Resource<Boolean> {
        val response = allUsersRemoteDataSource.getCurrentUser()
        return if (response is Resource.Success) {
            allUsersLocalDataSource.putUser(response.data!!)
            Resource.Success(true)
        } else {
            Resource.Error(response.message ?: "Failed to update user")
        }
    }

    override suspend fun getCurrentUserId(): Resource<Int> =
        allUsersLocalDataSource.getCurrentUserId()

    override suspend fun fetchCurrentUserId(): Resource<Int> =
        allUsersRemoteDataSource.getCurrentUserId()

    override suspend fun flowCurrentUserContactsIdList(): Flow<Resource<List<Int>>> =
        allUsersLocalDataSource.flowCurrentUserContactsIdList()

    override suspend fun updateUser(
        userModel: UserModel,
        refreshCache: Boolean
    ): Resource<Boolean> {
        val response = allUsersRemoteDataSource.updateUser(userModel)
        if (response is Resource.Success && refreshCache) {
            return refreshCurrentUser()
        }
        return response
    }

    override suspend fun getUsersByIdList(idList: List<Int>): List<UserModel> {
        return allUsersLocalDataSource.getUsersByIdList(idList)
    }

    override suspend fun getUsersByIdListExclude(idList: List<Int>): List<UserModel> {
        return allUsersLocalDataSource.getUsersByIdListExclude(idList)
    }

    override suspend fun addUserContact(id: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val response = allUsersRemoteDataSource.addContact(id)
        emit(
            if (response is Resource.Success) {
                allUsersLocalDataSource.addContact(id)
            } else {
                Resource.Error(response.message ?: "Failed to add user contact at remote")
            }
        )
    }

    override suspend fun removeUserContact(id: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val response = allUsersRemoteDataSource.removeContact(id)
        emit(
            if (response is Resource.Success) {
                allUsersLocalDataSource.removeContacts(listOf(id))
            } else {
                Resource.Error(response.message ?: "Failed to remove user contact at remote")
            }
        )
    }

    override suspend fun refreshUserContacts(): Resource<Boolean> {
        val response = allUsersRemoteDataSource.getUserContacts()
        if (response is Resource.Success) {
            return allUsersLocalDataSource.refreshUserContactList(response.data!!)
        }
        return Resource.Error(response.message ?: "Fail to fetch")
    }

    override suspend fun refreshAllUsers(): Resource<Boolean> {
        val response = allUsersRemoteDataSource.getAllUsers()
        if (response is Resource.Success) {
            return allUsersLocalDataSource.refreshAllUsersList(response.data!!)
        }
        return Resource.Error(response.message ?: "Fail to fetch")
    }
}