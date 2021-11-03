package tk.quietdev.level1.usecase

import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AuthRepository

class UserLoginUseCase(private val authRepository: AuthRepository) {
    suspend fun invoke(email: String, password: String): Resource<Boolean> {
        return authRepository.userLogin(email, password)
    }
}