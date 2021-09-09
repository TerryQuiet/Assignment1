package tk.quietdev.level1.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.data.db.model.RoomUser


@Dao
interface RoomUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: RoomUser)

    @Update
    suspend fun update(user: RoomUser)

    @Query("SELECT * from room_users WHERE id = :id")
    fun getUser(id: Int): Flow<RoomUser>

    @Query("SELECT * from room_users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<RoomUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUser(user: CurrentUser)




}