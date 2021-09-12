package tk.quietdev.level1.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.AllUsers
import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.data.db.model.RoomCurrentUserContacts


@Dao
interface RoomUserDao {

    // All users list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: AllUsers)

    @Query("SELECT * from room_all_users WHERE id = :id")
    fun getUser(id: Int): Flow<AllUsers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(list: List<AllUsers>)

    @Query("SELECT * from room_all_users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<AllUsers>>


    // Current user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUser(user: CurrentUser)

    @Update
    suspend fun updateCurrentUser(user: CurrentUser)

    @Query("SELECT * from room_current_user WHERE single = 1")
    suspend fun getCurrentUser(): CurrentUser

    @Query("SELECT * from room_current_user WHERE single = 1")
    fun getCurrentUserFlow(): Flow<CurrentUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUserContacts(list: List<RoomCurrentUserContacts>)


}