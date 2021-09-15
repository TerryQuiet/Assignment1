package tk.quietdev.level1.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "room_current_user_contacts_ids")
data class RoomUserContactsIds(
   @PrimaryKey(autoGenerate = false) val id: Int,
   //val userOwnerId: Int
)



