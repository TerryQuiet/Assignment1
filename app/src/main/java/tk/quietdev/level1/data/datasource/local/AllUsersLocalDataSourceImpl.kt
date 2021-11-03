package tk.quietdev.level1.data.datasource.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserListDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.utils.PrefsHelper

class AllUsersLocalDataSourceImpl(
    private val roomMapper: RoomMapper,
    private val dao: RoomUserListDao,
    private val prefsHelper: PrefsHelper,
    private val db: UserDatabase,
) : AllUsersLocalDataSource {
    override fun getAllUsersFlow(): Flow<Resource<List<UserModel>>> {
        TODO("Not yet implemented")
    }

    override fun getUserFlow(id: Int): Flow<Resource<UserModel>> =
        dao.getUser(id).map { Resource.Success(roomMapper.roomUserToDomainUser(it)) }


    override fun getIncludeUsersFlow(includeList: List<Int>): Flow<Resource<List<UserModel>>> {
        TODO("Not yet implemented")
    }

    override fun getExcludeUsersFlow(excludeList: List<Int>): Flow<Resource<List<UserModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun putUser(userModel: UserModel): Resource<Boolean> {
        return try {
            dao.insert(roomMapper.domainUserToRoomUser(userModel))
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "")
        }
    }

    override suspend fun setUserList(userList: List<UserModel>): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUserId(): Resource<Int> {
        val roomUsr = prefsHelper.getIntOrNull(prefsHelper.CURRENT_USER_ID)
        return if (roomUsr != null) Resource.Success(roomUsr) else Resource.Error("no user saved")
    }

    override suspend fun getCurrentUserContactsIdList(): Resource<List<Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun setCurrentUserId(userId: Int) {
        prefsHelper.saveInt(prefsHelper.CURRENT_USER_ID, userId)
    }

    override suspend fun setCurrentUserContactsIdList(idList: List<Int>) {
        TODO("Not yet implemented")
    }

    /*
    Map list<RoomUserContactId> to Resource Resource<List<Int>>
     */
    override suspend fun flowCurrentUserContactsIdList(): Flow<Resource<List<Int>>> =
        db.roomCurrentUserDao().getCurrentUserContactsIdsFlow()
            .map { listRoomId -> listRoomId.map { it.id } }.map { Resource.Success(it) }
}