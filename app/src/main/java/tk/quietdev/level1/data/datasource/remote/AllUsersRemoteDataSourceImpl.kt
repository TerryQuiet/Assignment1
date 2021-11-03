package tk.quietdev.level1.data.datasource.remote

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.UserApi
import tk.quietdev.level1.domain.models.UserModel

class AllUsersRemoteDataSourceImpl(
    private val api: UserApi,
    private val remoteMapper: RemoteMapper,
) : AllUsersRemoteDataSource {
    override suspend fun getAllUsers(): Resource<List<UserModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(userModel: UserModel): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserContacts(): Resource<List<UserModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): Resource<UserModel> {
        val response = api.getCurrentUser()
        if (response.isSuccessful) {
            val apiUser = response.body()?.data?.user
            apiUser?.let {
                return Resource.Success(remoteMapper.apiUserToDomainUser(it))
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getCurrentUserId(): Resource<Int> {
        val response = api.getCurrentUser()
        return if (response.isSuccessful) {
            val userId = response.body()?.data?.user?.id
            if (userId != null) Resource.Success(userId) else Resource.Error("failed")
        } else {
            val errorMessageFromApi =
                remoteMapper.shppApiErrorResponseMapper()
                    .fromJson(response.errorBody()?.string())?.message
            Resource.Error(errorMessageFromApi ?: "Error message parse fail")
        }
    }

    override suspend fun getCurrentUserContactsIdList(): Resource<List<Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeContact(contactId: Int): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addContact(contactId: Int): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun editUser(user: UserModel): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}