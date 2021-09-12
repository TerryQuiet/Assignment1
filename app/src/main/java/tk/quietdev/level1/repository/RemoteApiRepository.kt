package tk.quietdev.level1.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import tk.quietdev.level1.R
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.data.remote.models.AuthResponse
import tk.quietdev.level1.data.remote.models.AuthUser
import tk.quietdev.level1.data.remote.models.RemoteData
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.DataState
import tk.quietdev.level1.utils.UserRegisterError
import kotlin.reflect.KSuspendFunction1

class RemoteApiRepository(
    @ApplicationContext private val androidContext: Context,
    private val api: ShppApi,
    private val db: RoomUserDao,
    private val remoteMapper: RemoteMapper,
    private val roomMapper: RoomMapper,
) : Repository {

    private val apiErrorMapper by lazy { remoteMapper.moshiErrorResponseMapper() }

    override fun updateUser(updatedUserModel: UserModel) {
        Log.d("TAG", "updateUser called: ${updatedUserModel.userName}")
        MainScope().launch {
            withContext(Dispatchers.IO) {
                updatedUserModel.apply {
                    val updatedUser = db.getCurrentUser().copy(
                        address = physicalAddress,
                        birthday = birthDate,
                        career = occupation,
                        email = email,
                        name = userName,
                        phone = phone,
                    )
                    db.updateCurrentUser(updatedUser)
                }
            }
        }
        val data = RemoteData(remoteMapper.userToApiUser(updatedUserModel))
        // val data = remoteMapper.userToApiUser(updatedUserModel)
        MainScope().launch {
            withContext(Dispatchers.IO) {
                val header = HashMap<String, String>()
                header["Content-Type"] = "application/json"
                header["Authorization"] = "Bearer ${getCurrentUserToken()}"
                api.updateUser(header, data)
            }

        }
    }

    override fun addUser(userModel: UserModel): UserModel {
        TODO("Not yet implemented")
    }

    override fun getUserWithNoValidation(id: Int): UserModel? {
        TODO("Not yet implemented")
    }

    override fun getUserWithValidation(email: String, password: String): UserModel? {
        TODO("Not yet implemented")
    }

    override fun getUserList(amount: Int): List<UserModel> {
        TODO("Not yet implemented")
    }


    override suspend fun userRegistration(
        login: String,
        password: String
    ): Flow<DataState<UserModel>> =
        userAuth(AuthUser(login, password), api::userRegister)

    override suspend fun userLogin(login: String, password: String): Flow<DataState<UserModel>> =
        userAuth(AuthUser(login, password), api::userLogin)


    override fun currentUserFlow(): Flow<UserModel> =
        db.getCurrentUserFlow().map {
            roomMapper.currentUserToUser(it)
        }

    override fun getCurrentUserContactsFlow(): Flow<List<UserModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun cacheCurrentUserContactsFromApi() {
        val response = api.getCurrentUserContacts(getBearerToken())
        if (response.isSuccessful) {
            val contacts = response.body()
            contacts?.let { 

            }
        }
    }

    private suspend fun getCurrentUserToken(): String {
        return db.getCurrentUser().accessToken
    }


    private suspend fun userAuth(
        user: AuthUser,
        method: KSuspendFunction1<AuthUser, Response<AuthResponse>>
    ): Flow<DataState<UserModel>> = flow {
        emit(DataState.Loading)
        try {
            val response = method(user)
            if (response.isSuccessful) {
                val userTokenData = response.body()?.data
                userTokenData?.let {
                    val currentUser = remoteMapper.toRoomCurrentUser(it)
                    db.insertCurrentUser(currentUser)
                    remoteMapper.toUser(it)
                    emit(DataState.Success(remoteMapper.toUser(it)))
                }
            } else {
                val x = response.errorBody()?.string()
                val errorMessageFromApi =
                    apiErrorMapper.fromJson(x)?.message
                errorMessageFromApi?.let {
                    throw UserRegisterError(it)
                }
            }
        } catch (e: Exception) {
            var message = androidContext.getString(R.string.show_unknown_error)
            if (e is UserRegisterError) {
                e.message?.let {
                    message = it
                }
            }
            emit(DataState.Error(Exception(message)))
        }
    }.flowOn(Dispatchers.IO)


    // TODO: 9/10/2021 REDO, write to db only.
    fun getUserFromApi() = flow {
        try {
            val response = api.getCurrentUser(getBearerToken())
            if (response.isSuccessful) {
                val user = response.body()?.data?.user
                user?.let {
                    emit(remoteMapper.apiUserToUser(it))
                    // write to db if changed?
                }
            } else {
                val x = response.errorBody()?.string()
                val errorMessageFromApi =
                    apiErrorMapper.fromJson(x)?.message
                errorMessageFromApi?.let {
                    throw UserRegisterError(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun getBearerToken(): String {
        return "Bearer ${getCurrentUserToken()}"
    }


}