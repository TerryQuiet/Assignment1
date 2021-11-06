package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.UsersRepository

class RemoveUserContactUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke(id: Int): Flow<Resource<Boolean>> {
        return usersRepository.removeUserContact(id)
    }

}

