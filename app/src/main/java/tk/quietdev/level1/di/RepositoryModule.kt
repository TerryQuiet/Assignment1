package tk.quietdev.level1.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.db.RoomMapper
import tk.quietdev.level1.data.db.RoomUserDao
import tk.quietdev.level1.data.db.UserDatabase
import tk.quietdev.level1.data.remote.RemoteMapper
import tk.quietdev.level1.data.remote.ShppApi
import tk.quietdev.level1.repository.RemoteApiRepository
import tk.quietdev.level1.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /* @Singleton
     @Provides
     fun provideLocalRepository(
       @ApplicationContext context: Context,
       api:ShppApi
     ) : Repository = LocalRepositoryImp(context, api)*/

    @Singleton
    @Provides
    fun provideRemoteRepository(
        api: ShppApi,
        db : RoomUserDao,
        dbR: UserDatabase,
        @ApplicationContext context: Context,
        remoteMapper: RemoteMapper,
        roomMapper: RoomMapper
    ): Repository = RemoteApiRepository(context,api, db, dbR = dbR, remoteMapper, roomMapper)

}