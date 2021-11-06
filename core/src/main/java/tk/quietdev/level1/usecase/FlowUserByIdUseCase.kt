package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.UsersRepository
import tk.quietdev.level1.domain.models.UserModel

class FlowUserByIdUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke(uId: Int): Flow<Resource<UserModel>> = flow {
        emit(Resource.Loading())
        val flow = try {
            usersRepository.flowUserById(uId)
        } catch (e: Exception) {
            flow {
                emit(Resource.Error<UserModel>(e.message ?: "Failed to get userId"))
            }
        }
        emitAll(flow)
    }

}