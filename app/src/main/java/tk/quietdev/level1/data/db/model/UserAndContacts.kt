package tk.quietdev.level1.data.db.model

import androidx.room.Embedded
import androidx.room.Relation
import tk.quietdev.level1.data.remote.test.User

data class UserAndContacts(
    @Embedded val user: User,
    @Relation(
         parentColumn = "id",
         entityColumn = "contactOwnerId"
    )
    val contacts: RoomCurrentUserContacts
)