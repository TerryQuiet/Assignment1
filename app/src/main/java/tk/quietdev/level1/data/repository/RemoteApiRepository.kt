package tk.quietdev.level1.data.repository

import android.util.Log
import kotlinx.coroutines.flow.*
import retrofit2.Response
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.data.remote.models.ApiUserContactManipulation
import tk.quietdev.level1.data.remote.models.AuthResponse
import tk.quietdev.level1.data.remote.models.AuthUser
import tk.quietdev.level1.data.remote.models.GetUserResponse
import tk.quietdev.level1.data.remote.networkBoundResource
import tk.quietdev.level1.data.remote.test.GetUserContactsResponse
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Resource
import tk.quietdev.level1.utils.UserRegisterError
import kotlin.reflect.KSuspendFunction1

class RemoteApiRepository(
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
        saveFetchResult = { onGetUserResponse(it) },

    )

    override fun userRegistration(login: String, password: String) =
        userAuth(api::userRegister, AuthUser(email = login, password = password))

    override fun userLogin(login: String, password: String) =
        userAuth(api::userLogin, AuthUser(email = login, password = password))

    private fun userAuth(
        call: KSuspendFunction1<AuthUser, Response<AuthResponse>>,
        authUser: AuthUser
    ) = networkBoundResource(
        query = {
            db.getCurrentUserFlow().map {
                if (it == null) it else roomMapper.roomCurrentUserToUser(it)
            }
        },
        fetch = {
            Log.d("TAG", "userAuth: ")
            call(authUser)
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
                val errorMessageFromApi =
                    apiErrorMapper.fromJson(response.errorBody()?.string())?.message
                errorMessageFromApi?.let {
                    throw UserRegisterError(it)
                }
            }
        },
    )

    override fun currentUserFlow() = networkBoundResource(
        query = {
            flow {
                val userIds = db.getCurrentUser().id
                emitAll(db.getUser(userIds).map { roomMapper.roomUserToUser(it) })
            }
        },
        fetch = { api.getCurrentUser(getBearerToken()) },
        saveFetchResult = { onGetUserResponse(it) }
    )

    override fun getCurrentUserContactIdsFlow(shouldFetch: Boolean): Flow<Resource<List<Int>>> = networkBoundResource(
        query = {
            db.getCurrentUserContactsIdsFlow().map {
                it.map { it.id }
            }
        },
        fetch = { api.getCurrentUserContacts(getBearerToken()) },
        saveFetchResult = { onGetUserContactsResponse(it) },
        shouldFetch = { shouldFetch }
    )

    override fun getCurrentUserContactsFlow(list: List<Int>, shouldFetch: Boolean) =
        networkBoundResource(
            query = {
                db.getUsersByIds(list).map {

                    it.map { roomMapper.roomUserToUser(it) }
                }

            },
            fetch = { api.getCurrentUserContacts(getBearerToken()) },
            saveFetchResult = { onGetUserContactsResponse(it) },
            shouldFetch = { shouldFetch }
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

    override fun addUserContact(userModelId: Int) =
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
                    ApiUserContactManipulation(userModelId)
                )
            },
            saveFetchResult = { onGetUserContactsResponse(it) }
        )

    override fun removeUserContact(userModelId: Int) =
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
                    ApiUserContactManipulation(userModelId)
                )
            },
            saveFetchResult = { onGetUserContactsResponse(it) }
        )

    private suspend fun onGetUserContactsResponse(response: Response<GetUserContactsResponse>) {
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

    private suspend fun onGetUserResponse(response: Response<GetUserResponse>) {
        if (response.isSuccessful) {
            val apiUser = response.body()?.data?.user
            apiUser?.let {
                val roomUser = remoteMapper.apiUserToRoomUser(it)
                db.insert(roomUser)
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