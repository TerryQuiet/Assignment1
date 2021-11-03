package tk.quietdev.level1.data.datasource.auth

import tk.quietdev.level1.common.Resource

interface AuthTokenDataSource {
    fun saveToken(string: String)
    fun getToken(): String
    fun getUserId(): Resource<Int>
    fun putUserId(id: Int)
}