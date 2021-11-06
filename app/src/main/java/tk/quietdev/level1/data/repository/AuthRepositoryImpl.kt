package tk.quietdev.level1.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AuthRepository
import tk.quietdev.level1.data.datasource.auth.AuthTokenDataSource
import tk.quietdev.level1.data.datasource.auth.Token
import tk.quietdev.level1.data.datasource.auth.UserAuthDataSource
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSource
import tk.quietdev.level1.utils.httpIntercepors.TokenInterceptor

class AuthRepositoryImpl(
    private val tokenDataSource: AuthTokenDataSource,
    private val remoteAuthDataSource: UserAuthDataSource,
    private val tokenInterceptor: TokenInterceptor,
    private val allUsersRemoteDataSource: AllUsersRemoteDataSource
) : AuthRepository {
    override suspend fun userLogin(email: String, password: String): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            val response = remoteAuthDataSource.userLogin(email, password)
            if (response is Resource.Success) {
                emit(cacheTokenAndReturnResource(response))
            } else {
                emit(Resource.Error(response.message ?: ""))
            }
        }

    override suspend fun userRegister(email: String, password: String): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            val response = remoteAuthDataSource.userRegister(email, password)
            if (response is Resource.Success) {
                emit(cacheTokenAndReturnResource(response))
            } else {
                emit(Resource.Error(response.message ?: ""))
            }
        }

    override suspend fun cacheUserIdAndReturnResource(): Resource<Int> {
        tokenInterceptor.token = tokenDataSource.getToken()
        val currentUserId = allUsersRemoteDataSource.getCurrentUserId()
        return if (currentUserId is Resource.Success) {
            tokenDataSource.putUserId(currentUserId.data!!)
            Resource.Success(currentUserId.data!!)
        } else Resource.Error(currentUserId.message ?: "Failed to cache userId")
    }

    override suspend fun getCurrentUserId(): Resource<Int> = tokenDataSource.getUserId()

    override suspend fun autoLogin(): Resource<Boolean> {
        tokenInterceptor.token = tokenDataSource.getToken()
        val response = allUsersRemoteDataSource.getCurrentUser()
        return if (response is Resource.Success) {
            Resource.Success(true)
        } else {
            Resource.Error(response.message ?: "Auto login fail")
        }

    }

    private fun cacheTokenAndReturnResource(response: Resource<Token>) =
        if (response is Resource.Success) {
            response.data?.let {
                tokenDataSource.saveToken(it)
                tokenInterceptor.token = it
            }
            Resource.Success(true)
        } else Resource.Error(response.message ?: "Failed to cache token")
}