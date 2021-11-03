package tk.quietdev.level1.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.DeprecatedRoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomUserContactsIds

@Dao
interface RoomUserListDao {

    // All users list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: RoomUser)

    @Query("SELECT * from room_all_users WHERE id = :id")
    fun getUser(id: Int): Flow<RoomUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(list: List<RoomUser>)

    @Query("DELETE from room_all_users")
    fun clearUserList()

    @Query("SELECT * from room_all_users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<RoomUser>>

    // Current user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUser(userRoom: DeprecatedRoomCurrentUser)

    @Query("SELECT * from room_current_user WHERE single = 1")
    suspend fun getCurrentUser(): DeprecatedRoomCurrentUser

    @Query("SELECT * from room_current_user WHERE single = 1")
    fun getCurrentUserFlow(): Flow<DeprecatedRoomCurrentUser?>

    // by ID
    @Query("SELECT * FROM room_all_users where id IN (:ids)")
    fun getUsersByIds(ids: List<Int>): Flow<List<RoomUser>>

    @Query("SELECT * FROM room_all_users where id NOT IN (:ids)")
    fun getUsersExcludingId(ids: List<Int>): Flow<List<RoomUser>>

    @Query("SELECT * from room_current_user_contacts_ids ORDER BY id ASC")
    fun getCurrentUserContactsIdsFlow(): Flow<List<RoomUserContactsIds>>

    @Query("SELECT * from room_current_user_contacts_ids ORDER BY id ASC")
    suspend fun getCurrentUserContactsIds(): List<RoomUserContactsIds>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<RoomUserContactsIds>)

    @Query("DELETE from room_current_user_contacts_ids")
    suspend fun clearUserContactsList()


}