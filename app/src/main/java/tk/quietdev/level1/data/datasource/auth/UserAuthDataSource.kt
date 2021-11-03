package tk.quietdev.level1.data.datasource.auth

import tk.quietdev.level1.common.Resource

interface UserAuthDataSource {
    suspend fun userRegister(login: String, password: String): Resource<String>
    suspend fun userLogin(login: String, password: String): Resource<String>
}