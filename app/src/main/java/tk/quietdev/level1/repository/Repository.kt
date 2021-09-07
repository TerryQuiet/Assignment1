package tk.quietdev.level1.repository

import tk.quietdev.level1.models.UserModel

interface Repository  {
    fun updateUser(updatedUserModel: UserModel)
    fun addUser(userModel: UserModel): UserModel
    fun getUserWithNoValidation(id: Int): UserModel?
    fun getUserWithValidation(email: String, password: String): UserModel?
    fun getUserList(amount: Int = -1): List<UserModel>
    suspend fun <T> userRegistration(user :T)
}