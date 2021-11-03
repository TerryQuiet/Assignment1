package tk.quietdev.level1.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tk.quietdev.level1.data.AllUsersRepository
import tk.quietdev.level1.data.AuthRepository
import tk.quietdev.level1.usecase.FlowCurrentUserUseCase
import tk.quietdev.level1.usecase.RefreshCurrentUserUseCase
import tk.quietdev.level1.usecase.UserLoginUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UserCaseModule {

    @Provides
    fun provideUserLoginUseCase(
        authRepositoryImpl: AuthRepository
    ): UserLoginUseCase {
        return UserLoginUseCase(authRepository = authRepositoryImpl)
    }


    @Provides
    fun provideGetCurrenUserUseCase(
        allUserRepository: AllUsersRepository,
    ): FlowCurrentUserUseCase {
        return FlowCurrentUserUseCase(
            allUsersRepository = allUserRepository,
        )
    }

    @Provides
    fun refreshCurrentUser(
        allUserRepository: AllUsersRepository
    ): RefreshCurrentUserUseCase {
        return RefreshCurrentUserUseCase(
            allUsersRepository = allUserRepository
        )
    }

}