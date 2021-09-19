package tk.quietdev.level1.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.data.repository.RemoteApiRepository
import tk.quietdev.level1.data.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteRepository(
        api: ShppApi,
        db: RoomUserDao,
        @ApplicationContext context: Context,
        remoteMapper: RemoteMapper,
        roomMapper: RoomMapper
    ): Repository = RemoteApiRepository(
        db = db,
        api = api,
        androidContext = context,
        remoteMapper = remoteMapper,
        roomMapper = roomMapper
    )

}