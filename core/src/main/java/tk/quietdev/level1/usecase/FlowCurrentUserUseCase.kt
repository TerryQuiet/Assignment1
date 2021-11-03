package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.AllUsersRepository
import tk.quietdev.level1.domain.models.UserModel

class FlowCurrentUserUseCase(
    private val allUsersRepository: AllUsersRepository
) {
    suspend fun invoke(): Flow<Resource<UserModel>> = flow {
        emit(Resource.Loading())
        val uId = allUsersRepository.fetchCurrentUserId()
        val flow = if (uId is Resource.Success) {
            allUsersRepository.flowUserById(uId.data!!)
        } else {
            flow {
                emit(Resource.Error<UserModel>(uId.message ?: "Failed to get userId"))
            }
        }
        emitAll(flow)
    }

}

