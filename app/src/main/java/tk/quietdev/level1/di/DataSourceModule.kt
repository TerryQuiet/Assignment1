package tk.quietdev.level1.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.datasource.auth.AuthTokenDataSource
import tk.quietdev.level1.data.datasource.auth.AuthTokenDataSourceSharedPrefs
import tk.quietdev.level1.data.datasource.auth.UserAuthDataSource
import tk.quietdev.level1.data.datasource.auth.UserAuthDataSourceImpl
import tk.quietdev.level1.data.datasource.local.AllUsersLocalDataSource
import tk.quietdev.level1.data.datasource.local.AllUsersLocalDataSourceImpl
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSource
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSourceImpl
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserListDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.UserApi
import tk.quietdev.level1.utils.PrefsHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providePrefHelper(@ApplicationContext context: Context): PrefsHelper {
        return PrefsHelper(context)
    }

    @Singleton
    @Provides
    fun provideAuthTokenDataSource(prefsHelper: PrefsHelper): AuthTokenDataSource {
        return AuthTokenDataSourceSharedPrefs(prefsHelper)
    }

    @Singleton
    @Provides
    fun provideRemoteAuthDataSource(
        remoteMapper: RemoteMapper,
        api: UserApi
    ): UserAuthDataSource {
        return UserAuthDataSourceImpl(remoteMapper, api)
    }

    @Singleton
    @Provides
    fun providesAllUsersLocalDataSource(
        dao: RoomUserListDao,
        roomMapper: RoomMapper,
        db: UserDatabase,
        prefsHelper: PrefsHelper
    ): AllUsersLocalDataSource {
        return AllUsersLocalDataSourceImpl(
            roomMapper = roomMapper,
            dao = dao,
            db = db,
            prefsHelper = prefsHelper
        )
    }

    @Singleton
    @Provides
    fun provideAllUsersRemoteDataSource(
        remoteMapper: RemoteMapper,
        api: UserApi
    ): AllUsersRemoteDataSource {
        return AllUsersRemoteDataSourceImpl(
            api = api,
            remoteMapper = remoteMapper
        )
    }


}