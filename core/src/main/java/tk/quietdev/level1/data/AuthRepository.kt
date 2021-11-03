package tk.quietdev.level1.data

import tk.quietdev.level1.common.Resource

interface AuthRepository {
    suspend fun userLogin(email: String, password: String): Resource<Boolean>
    suspend fun userRegister(email: String, password: String): Resource<Boolean>
    suspend fun autoLogin(): Resource<Boolean>
}