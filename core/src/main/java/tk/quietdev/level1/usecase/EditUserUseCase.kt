package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.UsersRepository
import tk.quietdev.level1.domain.models.UserModel

class EditUserUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke(userModel: UserModel): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        emit(usersRepository.updateUser(userModel))
    }
}