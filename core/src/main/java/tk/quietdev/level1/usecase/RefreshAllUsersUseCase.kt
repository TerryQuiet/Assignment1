package tk.quietdev.level1.usecase

import tk.quietdev.level1.data.UsersRepository

class RefreshAllUsersUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke() {
        usersRepository.refreshAllUsers()
    }
}