package tk.quietdev.level1.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomUserContactsIds

@Dao
interface RoomUserListDao {

    // All users list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactIdList(user: RoomUser)

    @Query("SELECT * from room_all_users WHERE id = :id")
    fun getUser(id: Int): Flow<RoomUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(list: List<RoomUser>)

    @Query("DELETE from room_all_users")
    fun clearUserList()

    @Query("SELECT * from room_all_users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<RoomUser>>

    // by ID
    @Query("SELECT * FROM room_all_users where id IN (:ids)")
    fun flowGetUsersByIds(ids: List<Int>): Flow<List<RoomUser>>

    @Query("SELECT * FROM room_all_users where id IN (:ids)")
    suspend fun getUsersByIds(ids: List<Int>): List<RoomUser>

    @Query("SELECT * FROM room_all_users where id NOT IN (:ids)")
    fun flowUsersExcludingId(ids: List<Int>): Flow<List<RoomUser>>

    @Query("SELECT * FROM room_all_users where id NOT IN (:ids)")
    suspend fun getUsersExcludingId(ids: List<Int>): List<RoomUser>

    @Query("SELECT * from room_current_user_contacts_ids ORDER BY id ASC")
    fun getCurrentUserContactsIdsFlow(): Flow<List<RoomUserContactsIds>>

    @Delete
    suspend fun deleteUserContact(ids: List<RoomUserContactsIds>)

    @Insert
    suspend fun addUserContact(id: RoomUserContactsIds)

    @Query("SELECT * from room_current_user_contacts_ids ORDER BY id ASC")
    suspend fun getCurrentUserContactsIds(): List<RoomUserContactsIds>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactIdList(list: List<RoomUserContactsIds>)

    @Query("DELETE from room_current_user_contacts_ids")
    suspend fun clearUserContactsList()


}