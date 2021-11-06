package tk.quietdev.level1.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tk.quietdev.level1.common.Resource
import tk.quietdev.level1.data.UsersRepository
import tk.quietdev.level1.domain.models.UserModel

class FlowNotUserContactsUseCase(
    private val usersRepository: UsersRepository
) {
    suspend fun invoke(): Flow<Resource<List<UserModel>>> =
        usersRepository.flowCurrentUserContactsIdList().map {
            val currentUserId = usersRepository.getCurrentUserId().data
            val userContact = it.data ?: listOf()
            val listToExclude = userContact + listOfNotNull(currentUserId)
            val userList = usersRepository.getUsersByIdListExclude(listToExclude)
            Resource.Success(userList)
        }

}