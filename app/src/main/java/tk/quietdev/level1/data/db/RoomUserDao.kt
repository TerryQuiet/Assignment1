package tk.quietdev.level1.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.data.db.model.RoomUser


@Dao
interface roomUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: RoomUser)

    @Update
    suspend fun update(user: RoomUser)

    @Query("SELECT * from room_user WHERE id = :id")
    fun getUser(id: Int): Flow<RoomUser>

    @Query("SELECT * from room_user ORDER BY id ASC")
    fun getAllUsers(): Flow<List<RoomUser>>


}