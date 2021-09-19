package tk.quietdev.level1.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        remoteMapper: RemoteMapper,
        roomMapper: RoomMapper
    ): Repository = RemoteApiRepository(
        db = db,
        api = api,
        remoteMapper = remoteMapper,
        roomMapper = roomMapper
    )

}