package tk.quietdev.level1.usecase

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AllUsersRepository

class RefreshCurrentUserUseCase(
    private val allUsersRepository: AllUsersRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        return allUsersRepository.refreshCurrentUser()
    }
}