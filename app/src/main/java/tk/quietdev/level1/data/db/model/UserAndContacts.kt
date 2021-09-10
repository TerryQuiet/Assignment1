package tk.quietdev.level1.data.db.model

import androidx.room.Embedded
import androidx.room.Relation
import tk.quietdev.level1.data.remote.models.ApiUser

data class UserAndContacts(
    @Embedded val user: ApiUser,
    @Relation(
         parentColumn = "id",
         entityColumn = "contactOwnerId"
    )
    val contacts: RoomCurrentUserContacts
)