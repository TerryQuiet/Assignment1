package tk.quietdev.level1.di


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.db.RoomCurrentUserDao
import tk.quietdev.level1.data.db.RoomUserListDao
import tk.quietdev.level1.data.db.UserDatabase
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): UserDatabase {
        return Room
            .databaseBuilder(
                context,
                UserDatabase::class.java,
                UserDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRoomUserListDao(db: UserDatabase): RoomUserListDao {
        return db.roomUserListDao()
    }

    @Singleton
    @Provides
    fun provideRoomUser(db: UserDatabase): RoomCurrentUserDao {
        return db.roomCurrentUserDao()
    }
}