package tk.quietdev.level1.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.DeprecatedRoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomUserContactsIds

@Dao
interface RoomCurrentUserDao {

    // Current user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUser(userRoom: DeprecatedRoomCurrentUser)

    @Query("SELECT * from room_current_user WHERE single = 1")
    suspend fun getCurrentUser(): DeprecatedRoomCurrentUser

    @Query("SELECT * from room_current_user WHERE single = 1")
    fun getCurrentUserFlow(): Flow<DeprecatedRoomCurrentUser?>

    @Query("SELECT * from room_current_user_contacts_ids ORDER BY id ASC")
    fun getCurrentUserContactsIdsFlow(): Flow<List<RoomUserContactsIds>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<RoomUserContactsIds>)

    @Query("SELECT * from room_current_user_contacts_ids ORDER BY id ASC")
    suspend fun getCurrentUserContactsIds(): List<RoomUserContactsIds>

    @Query("DELETE from room_current_user_contacts_ids")
    suspend fun clearUserContactsList()


}