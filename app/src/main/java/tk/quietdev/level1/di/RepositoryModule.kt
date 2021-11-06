package tk.quietdev.level1.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.AuthRepository
import tk.quietdev.level1.data.UsersRepository
import tk.quietdev.level1.data.datasource.auth.AuthTokenDataSource
import tk.quietdev.level1.data.datasource.auth.UserAuthDataSource
import tk.quietdev.level1.data.datasource.local.AllUsersLocalDataSource
import tk.quietdev.level1.data.datasource.remote.AllUsersRemoteDataSource
import tk.quietdev.level1.data.repository.AuthRepositoryImpl
import tk.quietdev.level1.data.repository.UsersRepositoryImpl
import tk.quietdev.level1.utils.httpIntercepors.TokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {



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
    ): UsersRepository {
        return UsersRepositoryImpl(
            allUsersLocalDataSource = allUsersLocalDataSource,
            allUsersRemoteDataSource = allUsersRemoteDataSource,
        )
    }


}