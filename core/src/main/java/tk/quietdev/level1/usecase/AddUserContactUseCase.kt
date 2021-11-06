package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.UsersRepository

class AddUserContactUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke(id: Int): Flow<Resource<Boolean>> {
        return usersRepository.addUserContact(id)
    }

}

