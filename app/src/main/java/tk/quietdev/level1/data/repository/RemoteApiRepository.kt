package tk.quietdev.level1.data.repository

import android.util.Log
import androidx.room.withTransaction
import kotlinx.coroutines.flow.*
import retrofit2.Response
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.data.remote.models.*
import tk.quietdev.level1.data.remote.networkBoundResource
import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.Resource
import tk.quietdev.level1.utils.UserRegisterError
import tk.quietdev.level1.utils.httpIntercepors.TokenInterceptor
import kotlin.reflect.KSuspendFunction1

class RemoteApiRepository(
    private val api: ShppApi,
    private val dao: RoomUserDao,
    private val db: UserDatabase,
    private val remoteMapper: RemoteMapper,
    private val roomMapper: RoomMapper,
    private val tokenInterceptor: TokenInterceptor
) : Repository {
    private val shppApiErrorMapper by lazy { remoteMapper.shppApiErrorResponseMapper() }

    override fun updateUser(updatedUserModel: UserModel): Flow<Resource<UserModel?>> =
        networkBoundResource(
            query = {
                dao.getUser(updatedUserModel.id).map { roomMapper.roomUserToUser(it) }
            },
            fetch = {
                val data = remoteMapper.userToApiUserUpdate(updatedUserModel)
                api.updateUser(data)
            },
            saveFetchResult = { onGetUserResponse(it) }
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
            dao.getCurrentUserFlow().map {
                if (it == null) it else roomMapper.roomCurrentUserToUser(it)
            }
        },
        fetch = {
            call(authUser)
        },
        saveFetchResult = { response ->
            if (response.isSuccessful) {
                val userTokenData = response.body()?.data
                userTokenData?.let {
                    val currentUser = remoteMapper.toRoomCurrentUser(it)
                    val roomUser = remoteMapper.apiUserToRoomUser(it.user)
                    db.withTransaction {
                        tokenInterceptor.token = currentUser.accessToken
                        dao.insert(roomUser)
                        dao.insertCurrentUser(currentUser)
                    }
                }
            } else {
                val errorMessageFromApi =
                    shppApiErrorMapper.fromJson(response.errorBody()?.string())?.message
                errorMessageFromApi?.let {
                    throw UserRegisterError(it)
                }
            }
        },
    )

    override fun currentUserFlow(): Flow<Resource<UserModel?>> = networkBoundResource(
        query = {
            flow {
                val currentUser = dao.getCurrentUser()
                tokenInterceptor.token = currentUser.accessToken
                val userId = currentUser.id
                emitAll(dao.getUser(userId).map { roomMapper.roomUserToUser(it) })
            }
        },
        fetch = { api.getCurrentUser() },
        saveFetchResult = { onGetUserResponse(it) }
    )

    override fun getCurrentUserContactIdsFlow(shouldFetch: Boolean): Flow<Resource<List<Int>>> =
        networkBoundResource(
            query = {
                dao.getCurrentUserContactsIdsFlow().map {
                    it.map { it.id }
                }
            },
            fetch = { api.getCurrentUserContacts() },
            saveFetchResult = { onGetUserContactsResponse(it) },
            shouldFetch = { shouldFetch }
        )

    override fun getCurrentUserContactsFlow(list: List<Int>, shouldFetch: Boolean) =
        networkBoundResource(
            query = {
                dao.getUsersByIds(list).map {
                    it.map { roomMapper.roomUserToUser(it) }
                }

            },
            fetch = { api.getCurrentUserContacts() },
            saveFetchResult = { onGetUserContactsResponse(it) },
            shouldFetch = { shouldFetch }
        )

    override fun getAllUsersFlow() = networkBoundResource(
        query = {
            flow {
                val userIds =
                    dao.getCurrentUserContactsIdsFlow().first().map { it.id }.toMutableList()
                userIds.add(dao.getCurrentUser().id)
                emitAll(
                    dao.getUsersExcludingId(userIds)
                        .map { it.map { roomMapper.roomUserToUser(it) } })
            }
        },
        fetch = {
            api.getAllUsers(
                //getBearerToken()
            )
        },
        saveFetchResult = { response ->
            if (response.isSuccessful) {
                val apiUsers = response.body()?.data?.users
                apiUsers?.let {
                    val roomUsers = it.map { remoteMapper.apiUserToRoomUser(it) }
                    db.withTransaction {
                        dao.clearUserList()
                        dao.insertAllUsers(roomUsers)
                    }
                }
            } // todo implement on unsuccessful response
        }
    )

    override fun addUserContact(userModelId: Int) =
        networkBoundResource(
            query = {
                flow {
                    emitAll(dao.getCurrentUserContactsIds().map { it.id }.let {
                        dao.getUsersByIds(it).map { it.map { roomMapper.roomUserToUser(it) } }
                    })
                }
            },
            fetch = {
                api.addUserContact(ApiUserContactManipulation(userModelId))
            },
            saveFetchResult = { onGetUserContactsResponse(it) }
        )

    override fun removeUserContact(userModelId: Int) =
        networkBoundResource(
            query = {
                flow {
                    emitAll(dao.getCurrentUserContactsIds().map { it.id }.let {
                        dao.getUsersByIds(it).map { it.map { roomMapper.roomUserToUser(it) } }
                    })
                }
            },
            fetch = {
                api.removeUserContact(ApiUserContactManipulation(userModelId))
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
                db.withTransaction {
                    with(dao) {
                        clearUserContactsList()
                        insertAllUsers(userList)
                        insert(userIds)
                    }
                }

            }
        }
    }

    private suspend fun onGetUserResponse(response: Response<GetUserResponse>) {
        Log.d("TAG", "onGetUserResponse: $response")
        if (response.isSuccessful) {
            val apiUser = response.body()?.data?.user
            apiUser?.let {
                val roomUser = remoteMapper.apiUserToRoomUser(it)
                dao.insert(roomUser)
            }
        } else {
            val errorMessageFromApi =
                shppApiErrorMapper.fromJson(response.errorBody()?.string())?.message
            errorMessageFromApi?.let {
                throw UserRegisterError(it)
            }
        }
    }

}