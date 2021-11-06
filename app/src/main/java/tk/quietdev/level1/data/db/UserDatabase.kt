package tk.quietdev.level1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.quietdev.level1.data.db.model.RoomUser
import tk.quietdev.level1.data.db.model.RoomUserContactsIds

@Database(
    entities = [RoomUser::class, RoomUserContactsIds::class],
    version = 1,
    /*autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]*/
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun roomUserListDao(): RoomUserListDao

    companion object {
        const val DATABASE_NAME: String = "users_db"
    }


}