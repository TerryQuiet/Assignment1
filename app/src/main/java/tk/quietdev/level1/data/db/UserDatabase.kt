package tk.quietdev.level1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.data.db.model.RoomCurrentUserContacts
import tk.quietdev.level1.data.db.model.RoomUser

@Database(
    entities = [RoomUser::class, CurrentUser::class, RoomCurrentUserContacts::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun blogDao(): RoomUserDao

    companion object {
        val DATABASE_NAME: String = "users_db"
    }


}