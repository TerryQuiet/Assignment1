package tk.quietdev.level1.data.repository

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AllUsersRepository
import tk.quietdev.level1.data.datasource.local.AllUsersLocalDataSource
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSource
import tk.quietdev.level1.domain.models.UserModel

class AllUsersRepositoryImpl(
    private val allUsersRemoteDataSource: AllUsersRemoteDataSource,
    private val allUsersLocalDataSource: AllUsersLocalDataSource
) : AllUsersRepository {
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

    override suspend fun updateCurrentUserContactIdList() {
        TODO("Not yet implemented")
    }
}