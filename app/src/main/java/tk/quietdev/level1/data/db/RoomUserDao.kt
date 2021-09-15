package tk.quietdev.level1.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.RoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomUserContactsIds


@Dao
interface RoomUserDao {

    // All users list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: RoomUser)

    @Query("SELECT * from room_all_users WHERE id = :id")
    fun getUser(id: Int): Flow<RoomUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertAllUsers(list: List<RoomUser>)

    @Query("DELETE from room_all_users")
    fun _clearUserList()

    @Transaction
    open suspend fun insertAllUsers(list: List<RoomUser>) {
        _clearUserList()
        _insertAllUsers(list)
    }

    @Query("SELECT * from room_all_users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<RoomUser>>


    // Current user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertCurrentUser(userRoom: RoomCurrentUser)

    /*Note that the function with @Transaction needs to be open. Room will generate a
    concrete implementation of your DAO, either extending your abstract class or
    implementing your interface. To make @Transaction work, Room code-generates an
    overriding function that wraps a call to your implementation in a transaction.
    However, in Kotlin, concrete functions cannot be overridden without the open
    keyword. Leaving that keyword off may result in strange compile error messages.*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    open suspend fun insertCurrentUser(roomCurrentUser: RoomCurrentUser, roomUser: RoomUser) {
        insert(roomUser)
        _insertCurrentUser(roomCurrentUser)
    }

    @Query("SELECT * from room_current_user WHERE single = 1")
    suspend fun getCurrentUser(): RoomCurrentUser

    @Query("SELECT * from room_current_user WHERE single = 1")
    fun getCurrentUserFlow(): Flow<RoomCurrentUser>

/*    @Transaction
    open suspend fun insertCurrentUserContacts(list: List<RoomUserContacts>) {
        _clearCurrentUserContacts()
        _insertCurrentUserContacts(list)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertCurrentUserContacts(list: List<RoomUserContacts>)


    @Query("DELETE from room_current_user_contacts")
    fun _clearCurrentUserContacts()


    @Query("SELECT * from room_current_user_contacts ORDER BY id ASC")
    fun getCurrentUserContacts(): Flow<List<RoomUserContacts>>*/


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
    suspend fun _insert(list: List<RoomUserContactsIds>)

    @Query("DELETE from room_current_user_contacts_ids")
    fun _clearUserContactsList()

    @Transaction
    open suspend fun insert(listIds: List<RoomUserContactsIds>, roomUsers: List<RoomUser>) {
        _clearUserContactsList()
        _insertAllUsers(roomUsers)
        _insert(listIds)
    }

}