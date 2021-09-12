package tk.quietdev.level1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomCurrentUser
import tk.quietdev.level1.data.db.model.RoomCurrentUserContacts

@Database(
    entities = [RoomUser::class, RoomCurrentUser::class, RoomCurrentUserContacts::class],
    version = 1,
    /*autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]*/
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun blogDao(): RoomUserDao

    companion object {
        val DATABASE_NAME: String = "users_db"
    }


}