package tk.quietdev.level1.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_current_user_contacts")
data class RoomCurrentUserContacts(
   @PrimaryKey(autoGenerate = false) val id: Int,
)