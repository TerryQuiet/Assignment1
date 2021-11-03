package tk.quietdev.level1.data.repository

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AuthRepository
import tk.quietdev.level1.data.datasource.auth.AuthTokenDataSource
import tk.quietdev.level1.data.datasource.auth.UserAuthDataSource
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSource
import tk.quietdev.level1.utils.httpIntercepors.TokenInterceptor

class AuthRepositoryImpl(
    private val tokenDataSource: AuthTokenDataSource,
    private val remoteAuthDataSource: UserAuthDataSource,
    private val tokenInterceptor: TokenInterceptor,
    private val allUsersRemoteDataSource: AllUsersRemoteDataSource
) : AuthRepository {
    override suspend fun userLogin(email: String, password: String): Resource<Boolean> {
        val response = remoteAuthDataSource.userLogin(email, password)
        return extractResult(response)
    }

    override suspend fun userRegister(email: String, password: String): Resource<Boolean> {
        val response = remoteAuthDataSource.userRegister(email, password)
        return extractResult(response)
    }


    override suspend fun autoLogin(): Resource<Boolean> {
        tokenInterceptor.token = tokenDataSource.getToken()
        val response = allUsersRemoteDataSource.getCurrentUser()
        return Resource.Success(true)
    }

    private fun extractResult(response: Resource<String>) =
        if (response is Resource.Success) {
            response.data?.let {
                tokenDataSource.saveToken(it)
                tokenInterceptor.token = it
            }
            Resource.Success(true)
        } else Resource.Error(response.message ?: "")
}