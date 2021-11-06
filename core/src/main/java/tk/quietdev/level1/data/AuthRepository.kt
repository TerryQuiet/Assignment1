package tk.quietdev.level1.data

import kotlinx.coroutines.flow.Flow
import tk.quietdev.level1.common.Resource

interface AuthRepository {
    suspend fun userLogin(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun userRegister(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun autoLogin(): Resource<Boolean>
    suspend fun cacheUserIdAndReturnResource(): Resource<Int>
    suspend fun getCurrentUserId(): Resource<Int>
}