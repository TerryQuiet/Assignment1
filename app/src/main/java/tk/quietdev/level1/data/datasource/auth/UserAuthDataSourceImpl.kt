package tk.quietdev.level1.data.datasource.auth

import retrofit2.Response
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.UserApi
import tk.quietdev.level1.data.remote.models.AuthResponse
import tk.quietdev.level1.data.remote.models.AuthUser
import kotlin.reflect.KSuspendFunction1

class UserAuthDataSourceImpl(
    private val remoteMapper: RemoteMapper,
    private val api: UserApi,
) : UserAuthDataSource {
    override suspend fun userRegister(login: String, password: String): Resource<String> =
        userAuth(api::userRegister, AuthUser(email = login, password = password))

    override suspend fun userLogin(login: String, password: String): Resource<String> =
        userAuth(api::userLogin, AuthUser(email = login, password = password))

    private suspend fun userAuth(
        call: KSuspendFunction1<AuthUser, Response<AuthResponse>>,
        authUser: AuthUser
    ): Resource<String> {
        val response = call(authUser)
        if (response.isSuccessful) {
            val userTokenData = response.body()?.data?.accessToken
            userTokenData?.let {
                return Resource.Success(it)
            }
            return Resource.Error("Cannot parse token")
        } else {
            val errorMessageFromApi =
                remoteMapper.shppApiErrorResponseMapper()
                    .fromJson(response.errorBody()?.string())?.message
            return Resource.Error(errorMessageFromApi ?: "Cannot parse error message")
        }
    }
}