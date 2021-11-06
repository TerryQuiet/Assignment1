package tk.quietdev.level1.usecase

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AuthRepository

class AutoLoginUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(): Resource<Boolean> {
        return repository.autoLogin()
    }
}