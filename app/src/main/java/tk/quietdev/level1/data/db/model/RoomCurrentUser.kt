package tk.quietdev.level1.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_current_user")
data class RoomCurrentUser(
   @PrimaryKey(autoGenerate = false)  val single: Boolean = true,
   val id: Int,
   val accessToken: String
)