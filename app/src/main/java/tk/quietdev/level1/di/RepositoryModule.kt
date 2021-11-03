package tk.quietdev.level1.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.AllUsersRepository
import tk.quietdev.level1.data.AuthRepository
import tk.quietdev.level1.data.Repository
import tk.quietdev.level1.data.datasource.auth.AuthTokenDataSource
import tk.quietdev.level1.data.datasource.auth.UserAuthDataSource
import tk.quietdev.level1.data.datasource.local.AllUsersLocalDataSource
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSource
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserListDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.UserApi
import tk.quietdev.level1.data.repository.AllUsersRepositoryImpl
import tk.quietdev.level1.data.repository.AuthRepositoryImpl
import tk.quietdev.level1.data.repository.RemoteApiRepository
import tk.quietdev.level1.utils.httpIntercepors.TokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteRepository(
        api: UserApi,
        dao: RoomUserListDao,
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
    fun provideAuthRepository(
        tokenInterceptor: TokenInterceptor,
        dataSourceImpl: UserAuthDataSource,
        dataSourceSharedPrefs: AuthTokenDataSource,
        allUsersRemoteDataSource: AllUsersRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(
            tokenInterceptor = tokenInterceptor,
            remoteAuthDataSource = dataSourceImpl,
            tokenDataSource = dataSourceSharedPrefs,
            allUsersRemoteDataSource = allUsersRemoteDataSource
        )
    }


    @Singleton
    @Provides
    fun provideAllUsersRepository(
        allUsersLocalDataSource: AllUsersLocalDataSource,
        allUsersRemoteDataSource: AllUsersRemoteDataSource,
    ): AllUsersRepository {
        return AllUsersRepositoryImpl(
            allUsersLocalDataSource = allUsersLocalDataSource,
            allUsersRemoteDataSource = allUsersRemoteDataSource,
        )
    }


}