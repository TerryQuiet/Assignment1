package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AuthRepository

class UserRegsterUseCase(private val authRepository: AuthRepository) {
    suspend fun invoke(email: String, password: String): Flow<Resource<Boolean>> {
        return authRepository.userRegister(email, password)
    }
}