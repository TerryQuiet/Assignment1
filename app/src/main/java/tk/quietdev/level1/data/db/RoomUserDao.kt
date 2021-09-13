package tk.quietdev.level1.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomCurrentUserContacts


@Dao
interface RoomUserDao {

    // All users list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: RoomUser)

    @Query("SELECT * from room_all_users WHERE id = :id")
    fun getUser(id: Int): Flow<RoomUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(list: List<RoomUser>)

    @Query("SELECT * from room_all_users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<RoomUser>>

    // Current user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun _insertCurrentUser(userRoom: RoomCurrentUser)

    /*Note that the function with @Transaction needs to be open. Room will generate a
    concrete implementation of your DAO, either extending your abstract class or
    implementing your interface. To make @Transaction work, Room code-generates an
    overriding function that wraps a call to your implementation in a transaction.
    However, in Kotlin, concrete functions cannot be overridden without the open
    keyword. Leaving that keyword off may result in strange compile error messages.*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    open suspend  fun insertCurrentUser(roomCurrentUser: RoomCurrentUser, roomUser: RoomUser) {
        insert(roomUser)
        _insertCurrentUser(roomCurrentUser)
    }

    @Query("SELECT * from room_current_user WHERE single = 1")
    suspend fun getCurrentUser(): RoomCurrentUser

    @Query("SELECT * from room_current_user WHERE single = 1")
    fun getCurrentUserFlow(): Flow<RoomCurrentUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUserContacts(list: List<RoomCurrentUserContacts>)

    @Query("SELECT * from room_current_user_contacts ORDER BY id ASC")
    fun getCurrentUserContactsIds(): Flow<List<Int>>

    @Query("SELECT * FROM room_all_users where id IN (:ids)")
    fun getUsersByIds(ids: List<Int>): Flow<List<RoomUser>>


}