package tk.quietdev.level1.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.data.repository.AccessTokenRepositoryImpl
import tk.quietdev.level1.data.repository.RemoteApiRepository
import tk.quietdev.level1.data.repository.Repository
import tk.quietdev.level1.utils.httpIntercepors.TokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteRepository(
        api: ShppApi,
        dao: RoomUserDao,
        db: UserDatabase,
        tokenInterceptor: TokenInterceptor,
        remoteMapper: RemoteMapper,
        roomMapper: RoomMapper
    ): Repository = RemoteApiRepository(
        dao = dao,
        db = db,
        api = api,
        remoteMapper = remoteMapper,
        roomMapper = roomMapper,
        tokenInterceptor = tokenInterceptor
    )

    @Singleton
    @Provides
    fun provideAccessTokenRepository(
        db: RoomUserDao,
    ): AccessTokenRepositoryImpl = AccessTokenRepositoryImpl(
        db = db,
    )

}