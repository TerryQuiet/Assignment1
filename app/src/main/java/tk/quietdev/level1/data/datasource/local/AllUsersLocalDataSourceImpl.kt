package tk.quietdev.level1.data.datasource.local

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserListDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.data.db.model.RoomUserContactsIds
import tk.quietdev.level1.domain.models.UserModel
import tk.quietdev.level1.utils.PrefsHelper

class AllUsersLocalDataSourceImpl(
    private val roomMapper: RoomMapper,
    private val dao: RoomUserListDao,
    private val prefsHelper: PrefsHelper,
    private val db: UserDatabase,
) : AllUsersLocalDataSource {

    override fun getUserFlow(id: Int): Flow<Resource<UserModel>> =
        dao.getUser(id).map { Resource.Success(roomMapper.roomUserToDomainUser(it)) }

    override suspend fun removeContacts(toRemove: List<Int>): Resource<Boolean> {
        return try {
            db.roomUserListDao().deleteUserContact(toRemove.map { RoomUserContactsIds(it) })
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to remove contact[s] from db")
        }
    }

    override suspend fun addContact(id: Int): Resource<Boolean> {
        return try {
            db.roomUserListDao().addUserContact(RoomUserContactsIds(id))
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to add contact to db")
        }

    }

    override suspend fun getUsersByIdListExclude(excludeList: List<Int>): List<UserModel> {
        return db.roomUserListDao().getUsersExcludingId(excludeList)
            .map { roomMapper.roomUserToDomainUser(it) }
    }

    override suspend fun putUser(userModel: UserModel): Resource<Boolean> {
        return try {
            dao.insertContactIdList(roomMapper.domainUserToRoomUser(userModel))
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "")
        }
    }

    override suspend fun refreshUserContactList(userList: List<UserModel>): Resource<Boolean> {
        val userId = userList.map { RoomUserContactsIds(it.id) }
        val roomUsers = userList.map { roomMapper.domainUserToRoomUser(it) }
        return try {
            db.withTransaction {
                with(dao) {
                    clearUserContactsList()
                    insertAllUsers(roomUsers)
                    insertContactIdList(userId)
                }
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update db")
        }
    }

    override suspend fun refreshAllUsersList(userList: List<UserModel>): Resource<Boolean> {
        val roomUsers = userList.map { roomMapper.domainUserToRoomUser(it) }
        return try {
            db.withTransaction {
                with(dao) {
                    clearUserList()
                    insertAllUsers(roomUsers)
                }
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update db")
        }
    }


    override suspend fun getCurrentUserId(): Resource<Int> {
        val roomUsr = prefsHelper.getIntOrNull(prefsHelper.CURRENT_USER_ID)
        return if (roomUsr != null) Resource.Success(roomUsr) else Resource.Error("no user saved")
    }

    override suspend fun setCurrentUserId(userId: Int) {
        prefsHelper.saveInt(prefsHelper.CURRENT_USER_ID, userId)
    }


    override suspend fun getUsersByIdList(idList: List<Int>): List<UserModel> {
        return dao.getUsersByIds(idList).map { roomMapper.roomUserToDomainUser(it) }
    }

    /*
    Map list<RoomUserContactId> to Resource Resource<List<Int>>
     */
    override fun flowCurrentUserContactsIdList(): Flow<Resource<List<Int>>> =
        db.roomUserListDao().getCurrentUserContactsIdsFlow()
            .map { listRoomId -> listRoomId.map { it.id } }.map { Resource.Success(it) }
}