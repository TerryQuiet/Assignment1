package tk.quietdev.level1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.quietdev.level1.data.db.model.AllUsers
import tk.quietdev.level1.data.db.model.CurrentUser
import tk.quietdev.level1.data.db.model.RoomCurrentUserContacts

@Database(
    entities = [AllUsers::class, CurrentUser::class, RoomCurrentUserContacts::class],
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