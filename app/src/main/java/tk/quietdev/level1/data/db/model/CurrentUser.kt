package tk.quietdev.level1.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_current_user")
data class CurrentUser(
   @PrimaryKey(autoGenerate = false)  val single: Boolean = true,
   val id: Int,
   val address: String?,
   val birthday: String?,
   val career: String?,
   val createdAt: String?,
   val email: String,
   val name: String?,
   val phone: String?,
   val updatedAt: String?,
   val accessToken: String
)