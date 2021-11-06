package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.UsersRepository
import tk.quietdev.level1.domain.models.UserModel

class FlowUserContactsUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke(): Flow<Resource<List<UserModel>>> =
        usersRepository.flowCurrentUserContactsIdList().map {
            val userList = usersRepository.getUsersByIdList(it.data!!)
            Resource.Success(userList)
        }

}