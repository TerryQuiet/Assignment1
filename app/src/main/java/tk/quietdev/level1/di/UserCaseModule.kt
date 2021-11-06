package tk.quietdev.level1.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.data.AuthRepository
import tk.quietdev.level1.data.UsersRepository
import tk.quietdev.level1.usecase.*

@Module
//@InstallIn(ViewModelComponent::class)
@InstallIn(SingletonComponent::class)
object UserCaseModule {

    @Provides
    fun provideUserLoginUseCase(
        authRepositoryImpl: AuthRepository
    ): UserLoginUseCase {
        return UserLoginUseCase(authRepository = authRepositoryImpl)
    }


    @Provides
    fun provideGetCurrenUserUseCase(
        userRepository: UsersRepository,
    ): FlowCurrentUserUseCase {
        return FlowCurrentUserUseCase(
            usersRepository = userRepository,
        )
    }

    @Provides
    fun refreshCurrentUser(
        userRepository: UsersRepository
    ): RefreshCurrentUserUseCase {
        return RefreshCurrentUserUseCase(
            usersRepository = userRepository
        )
    }

    @Provides
    fun autoLogin(
        authRepository: AuthRepository
    ): AutoLoginUseCase {
        return AutoLoginUseCase(
            repository = authRepository
        )
    }


    @Provides
    fun userEdit(
        userRepository: UsersRepository
    ): EditUserUseCase {
        return EditUserUseCase(
            usersRepository = userRepository,
        )
    }

    @Provides
    fun flowContacts(
        userRepository: UsersRepository
    ): FlowUserContactsUseCase {
        return FlowUserContactsUseCase(
            usersRepository = userRepository
        )
    }

    @Provides
    fun refreshContacts(
        userRepository: UsersRepository
    ): RefreshUserContactsUseCase {
        return RefreshUserContactsUseCase(
            usersRepository = userRepository
        )
    }


    @Provides
    fun refreshAllUsers(
        userRepository: UsersRepository
    ): RefreshAllUsersUseCase {
        return RefreshAllUsersUseCase(
            usersRepository = userRepository
        )
    }

    @Provides
    fun flowAllUsers(
        userRepository: UsersRepository
    ): FlowNotUserContactsUseCase {
        return FlowNotUserContactsUseCase(
            usersRepository = userRepository
        )
    }

    @Provides
    fun addUserContactUseCase(
        userRepository: UsersRepository
    ): AddUserContactUseCase {
        return AddUserContactUseCase(
            usersRepository = userRepository
        )
    }

    @Provides
    fun removeUserContactsUseCase(
        userRepository: UsersRepository
    ): RemoveUserContactUseCase {
        return RemoveUserContactUseCase(
            usersRepository = userRepository
        )
    }

    @Provides
    fun flowUserByIdUseCase(
        userRepository: UsersRepository
    ): FlowUserByIdUseCase {
        return FlowUserByIdUseCase(
            usersRepository = userRepository
        )
    }

    @Provides
    fun regUseCase(
        authRepositoryImpl: AuthRepository
    ): UserRegsterUseCase {
        return UserRegsterUseCase(
            authRepository = authRepositoryImpl
        )
    }


}