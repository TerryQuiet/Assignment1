package tk.quietdev.level1.usecase

import tk.quietdev.level1.data.UsersRepository

class RefreshUserContactsUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke() {
        usersRepository.refreshUserContacts()
    }
}