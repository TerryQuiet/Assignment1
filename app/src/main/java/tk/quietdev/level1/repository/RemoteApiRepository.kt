package tk.quietdev.level1.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.data.remote.models.ApiUserContactManipulation
import tk.quietdev.level1.data.remote.models.AuthUser
import tk.quietdev.level1.data.remote.test.GetUserContactsResponse
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.UserRegisterError
import tk.quietdev.level1.utils.networkBoundResource

class RemoteApiRepository(
    @ApplicationContext private val androidContext: Context,
    private val api: ShppApi,
    private val db: RoomUserDao,
    private val remoteMapper: RemoteMapper,
    private val roomMapper: RoomMapper,
) : Repository {

    private val apiErrorMapper by lazy { remoteMapper.moshiErrorResponseMapper() }

    override fun updateUser(updatedUserModel: UserModel) = networkBoundResource(
        query = {
            db.getUser(updatedUserModel.id).map { roomMapper.roomUserToUser(it) }
        },
        fetch = {
            val data = remoteMapper.userToApiUserUpdate(updatedUserModel)
            api.updateUser(getAuthHeaders(), data)
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

    override fun userRegistration(login: String, password: String) = networkBoundResource(
        query = {
            flow {
                val userIds = db.getCurrentUser().id
                emitAll(db.getUser(userIds).map { roomMapper.roomUserToUser(it) })
            }
        },
        fetch = {
            api.userRegister(AuthUser(login, password))
        },
        saveFetchResult = { response ->
            if (response.isSuccessful) {
                val userTokenData = response.body()?.data
                userTokenData?.let {
                    val currentUser = remoteMapper.toRoomCurrentUser(it)
                    val roomUser = remoteMapper.apiUserToRoomUser(it.user)
                    db.insertCurrentUser(currentUser, roomUser)
                }
            } else {
                val x = response.errorBody()?.string()
                val errorMessageFromApi =
                    apiErrorMapper.fromJson(x)?.message
                errorMessageFromApi?.let {
                    throw UserRegisterError(it)
                }
            }
        },
    )

    override fun userLogin(login: String, password: String) = networkBoundResource(
        query = {
            flow {
                val userIds = db.getCurrentUser().id
                emitAll(db.getUser(userIds).map { roomMapper.roomUserToUser(it) })
            }
        },
        fetch = {
            api.userLogin(AuthUser(login, password))
        },
        saveFetchResult = { response ->
            if (response.isSuccessful) {
                val userTokenData = response.body()?.data
                userTokenData?.let {
                    val currentUser = remoteMapper.toRoomCurrentUser(it)
                    val roomUser = remoteMapper.apiUserToRoomUser(it.user)
                    db.insertCurrentUser(currentUser, roomUser)
                }
            } else {
                val x = response.errorBody()?.string()
                val errorMessageFromApi =
                    apiErrorMapper.fromJson(x)?.message
                errorMessageFromApi?.let {
                    throw UserRegisterError(it)
                }
            }
        },
    )

    // good
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


    fun getCurrentUserContactIdsFlow() = db.getCurrentUserContactsIdsFlow().map { it.map { it.id } }
    fun getCurrentUserContactsFlow(list: List<Int>) =
        networkBoundResource(
            query = { db.getUsersByIds(list).map { it.map { roomMapper.roomUserToUser(it) } } },
            fetch = { api.getCurrentUserContacts(getBearerToken()) },
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

    override fun addUserContact(userModel: UserModel) =
        networkBoundResource(
            query = {
                flow {
                    emitAll(db.getCurrentUserContactsIds().map { it.id }.let {
                        db.getUsersByIds(it).map { it.map { roomMapper.roomUserToUser(it) } }
                    })
                }
            },
            fetch = {
                api.addUserContact(
                    getAuthHeaders(),
                    ApiUserContactManipulation(userModel.id)
                )
            },
            saveFetchResult = { onContactManipulationResponse(it) }
        )

    override fun removeUserContact(userModel: UserModel) =
        networkBoundResource(
            query = {
                flow {
                    emitAll(db.getCurrentUserContactsIds().map { it.id }.let {
                        db.getUsersByIds(it).map { it.map { roomMapper.roomUserToUser(it) } }
                    })
                }
            },
            fetch = {
                api.removeUserContact(
                    getAuthHeaders(),
                    ApiUserContactManipulation(userModel.id)
                )
            },
            saveFetchResult = { onContactManipulationResponse(it) }
        )


    private suspend fun onContactManipulationResponse(response: Response<GetUserContactsResponse>) {
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


}