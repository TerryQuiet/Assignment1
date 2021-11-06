package tk.quietdev.level1.data.datasource.remote

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.UserApi
import tk.quietdev.level1.data.remote.models.ApiUserContactManipulation
import tk.quietdev.level1.domain.models.UserModel

class AllUsersRemoteDataSourceImpl(
    private val api: UserApi,
    private val remoteMapper: RemoteMapper,
) : AllUsersRemoteDataSource {
    override suspend fun getAllUsers(): Resource<List<UserModel>> {
        val response = api.getAllUsers()
        if (response.isSuccessful) {
            val userList =
                response.body()?.data?.users?.map {
                    remoteMapper.apiUserToDomainUser(it)
                }
            userList?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message() ?: "Failed to get userContacts")
    }

    override suspend fun updateUser(userModel: UserModel): Resource<Boolean> {
        val updatedUser = remoteMapper.userToApiUserUpdate(userModel)
        val response = api.updateUser(updatedUser)
        return if (response.isSuccessful) {
            Resource.Success(true)
        } else Resource.Error("todo")
    }

    override suspend fun getUserContacts(): Resource<List<UserModel>> {
        val response = api.getCurrentUserContacts()
        if (response.isSuccessful) {
            val userList =
                response.body()?.data?.contacts?.map {
                    remoteMapper.apiUserToDomainUser(it)
                }
            userList?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message() ?: "Failed to get userContacts")
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
        val response = api.removeUserContact(ApiUserContactManipulation(contactId))
        return if (response.isSuccessful) {
            Resource.Success(true)
        } else {
            Resource.Error(response.message())
        }
    }

    override suspend fun addContact(contactId: Int): Resource<Boolean> {
        val response = api.addUserContact(ApiUserContactManipulation(contactId))
        return if (response.isSuccessful) {
            Resource.Success(true)
        } else {
            Resource.Error(response.message())
        }
    }

    override suspend fun editUser(user: UserModel): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}