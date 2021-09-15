package tk.quietdev.level1.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import tk.quietdev.level1.R
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.data.remote.models.ApiUserContactManipulation
import tk.quietdev.level1.data.remote.models.AuthResponse
import tk.quietdev.level1.data.remote.models.AuthUser
import tk.quietdev.level1.data.remote.models.RemoteData
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.DataState
import tk.quietdev.level1.utils.UserRegisterError
import tk.quietdev.level1.utils.networkBoundResource
import kotlin.reflect.KSuspendFunction1

class RemoteApiRepository(
    @ApplicationContext private val androidContext: Context,
    private val api: ShppApi,
    private val db: RoomUserDao,
    private val dbR: UserDatabase,
    private val remoteMapper: RemoteMapper,
    private val roomMapper: RoomMapper,
) : Repository {

    private val apiErrorMapper by lazy { remoteMapper.moshiErrorResponseMapper() }

    override suspend fun updateUser(updatedUserModel: UserModel) {
        Log.d("TAG", "updateUser called: ${updatedUserModel.userName}")
        /*       MainScope().launch {
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
               }*/
        val data = RemoteData(remoteMapper.userToApiUser(updatedUserModel))
        // val data = remoteMapper.userToApiUser(updatedUserModel)
        MainScope().launch {
            withContext(Dispatchers.IO) {
                api.updateUser(getAuthHeaders(), data)
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
    ): Flow<DataState<UserModel>> = userAuth(AuthUser(login, password), api::userRegister)

    override suspend fun userLogin(login: String, password: String): Flow<DataState<UserModel>> =
        userAuth(AuthUser(login, password), api::userLogin)

    override fun currentUserFlow() = networkBoundResource(
        query = {
            flow {
                val userIds = db.getCurrentUser().id
                emitAll(db.getUser(userIds).map { roomMapper.roomUserToUser(it) })
            }
        },
        fetch = {
            api.getCurrentUser(getBearerToken())
        },
        saveFetchResult = { response ->
            if (response.isSuccessful) {
                val apiUser = response.body()?.data?.user
                apiUser?.let {
                    val roomUser = remoteMapper.apiUserToRoomUser(it)
                    db.insert(roomUser)
                }
            } // todo implement on unsuccessful response
        }
    )


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

                    val roomUser = remoteMapper.apiUserToRoomUser(it.user)
                    db.insertCurrentUser(currentUser, roomUser)
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
            e.printStackTrace()
            emit(DataState.Error(Exception(message)))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getBearerToken(): String {
        return "Bearer ${getCurrentUserToken()}"
    }

    private suspend fun getCurrentUserToken(): String {
        return db.getCurrentUser().accessToken
    }

    private suspend fun getAuthHeaders(): HashMap<String, String> {
        val header = HashMap<String, String>()
        header["Content-Type"] = "application/json"
        header["Authorization"] = "Bearer ${getCurrentUserToken()}"
        return header
    }

    override fun getCurrentUserContactsFlow() =
        networkBoundResource(
            query = {
                  flow { emitAll(db.getCurrentUserContactsIds().map { it.id }.let { db.getUsersByIds(it).map { it.map { roomMapper.roomUserToUser(it) } } }) }
            },
            fetch = {
                api.getCurrentUserContacts(getBearerToken())
            },
            saveFetchResult = { response ->
                if (response.isSuccessful) {
                    val userIds =
                        response.body()?.data?.contacts?.map {
                            remoteMapper.apiUserToID(it)
                        }
                    val userList =
                        response.body()?.data?.contacts?.map {
                            remoteMapper.apiUserToRoomUser(it)
                        }
                    if (userIds != null && userList != null) {
                        db.insert(userIds, userList)
                    }
                }

            } // todo implement on unsuccessful response
        )

    fun getCurrentUserContactIdsFlow() = db.getCurrentUserContactsIdsFlow().map { it.map { it.id } }
    fun getCurrentUserContactsFlow(list: List<Int>) =
        networkBoundResource(
            query = {
                Log.d("TAG", "getCurrentUserContactsFlow: ${list.size}")
                db.getUsersByIds(list).map { it.map { roomMapper.roomUserToUser(it) } }
            },
            fetch = {
                api.getCurrentUserContacts(getBearerToken())
            },
            saveFetchResult = { response ->
                if (response.isSuccessful) {
                    val userIds =
                        response.body()?.data?.contacts?.map {
                            remoteMapper.apiUserToID(it)
                        }
                    val userList =
                        response.body()?.data?.contacts?.map {
                            remoteMapper.apiUserToRoomUser(it)
                        }
                    if (userIds != null && userList != null) {
                        db.insert(userIds, userList)
                    }
                }

            } // todo implement on unsuccessful response
        )


    fun getAllUsersFlow2() = networkBoundResource(
        query = {
            flow {
                var userIds =
                    db.getUsersByIds(listOf(2)).first().map { it.id }

                emitAll(
                    db.getUsersByIds(userIds).map { it.map { roomMapper.roomUserToUser(it) } })
            }
        },
        fetch = {
            api.getAllUsers(getBearerToken())
        },
        saveFetchResult = { response ->
            if (response.isSuccessful) {
                val apiUsers = response.body()?.data?.users
                apiUsers?.let {
                    val roomUsers = it.map { remoteMapper.apiUserToRoomUser(it) }
                    db.insertAllUsers(roomUsers)
                }
            } // todo implement on unsuccessful response
        }
    )

    override fun getAllUsersFlow() = networkBoundResource(
        query = {
            flow {
                val userIds =
                    db.getCurrentUserContactsIdsFlow().first().map { it.id }.toMutableList()
                userIds.add(db.getCurrentUser().id)
                emitAll(
                    db.getUsersExcludingId(userIds)
                        .map { it.map { roomMapper.roomUserToUser(it) } })
            }
        },
        fetch = {
            api.getAllUsers(getBearerToken())
        },
        saveFetchResult = { response ->
            if (response.isSuccessful) {
                val apiUsers = response.body()?.data?.users
                apiUsers?.let {
                    val roomUsers = it.map { remoteMapper.apiUserToRoomUser(it) }
                    db.insertAllUsers(roomUsers)
                }
            } // todo implement on unsuccessful response
        }
    )

    override suspend fun addUserContact(userModel: UserModel) {
        Log.d("TAG", "addUserContact: ")
        api.addUserContact(getAuthHeaders(), ApiUserContactManipulation(userModel.id))
    }

    override suspend fun removeUserContact(userModel: UserModel) {
        networkBoundResource(
            fetch = {
                api.removeUserContact(
                    getAuthHeaders(),
                    ApiUserContactManipulation(userModel.id)
                )
            },
            saveFetchResult = { response ->
                if (response.isSuccessful) {
                    val userIds =
                        response.body()?.data?.contacts?.map {
                            remoteMapper.apiUserToID(it)
                        }
                    val userList =
                        response.body()?.data?.contacts?.map {
                            remoteMapper.apiUserToRoomUser(it)
                        }
                    if (userIds != null && userList != null) {
                        db.insert(userIds, userList)
                    }
                }
            }
        )
    }


}