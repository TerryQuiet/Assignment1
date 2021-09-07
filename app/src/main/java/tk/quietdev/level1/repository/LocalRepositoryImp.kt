package tk.quietdev.level1.repository


import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import tk.quietdev.level1.api.ShppApi

import tk.quietdev.level1.models.UserModel
import tk.quietdev.level1.utils.ext.readAssetsFile


class LocalRepositoryImp(
    context: Context,
    private val api: ShppApi,
) : Repository {

    private var userIds: Int = 0

    // all the users in database, using Int as ID
    private val allFakeUsers: MutableMap<Int, UserModel> = emptyMap<Int, UserModel>().toMutableMap()


    init {
        addUsersFromJson(context.assets.readAssetsFile("json/FakeUserArray.json"))
    }

    private fun isPasswordCorrect(userModel: UserModel?, password: String): Boolean {
        return (userModel?.password == password)
    }


    private fun addUsersFromJson(string: String) {
        val mapper = jacksonObjectMapper()
        val userModelList: List<UserModel> = mapper.readValue(string)
        userModelList.forEach { addUser(it) }
    }


    private fun findIdByEmail(email: String): Int? {
        return allFakeUsers.values.find { it.email == email }?._id
    }


    /**
     * @param amount the number of users to return, -1 if all
     * @return list of users
     */
    override fun getUserList(amount: Int): List<UserModel> {
        return allFakeUsers.values.take(if (amount < 0) allFakeUsers.size else amount)
    }

    override suspend fun <T> userRegistration(user: T) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserWithValidation(email: String, password: String): UserModel? {
        val id = findIdByEmail(email)
        return if (isPasswordCorrect(allFakeUsers[id], password)) {
            allFakeUsers[id]
        } else null
    }


    override fun getUserWithNoValidation(id: Int): UserModel? {
        return allFakeUsers[id]
    }

    /**
     * @return user with Id assigned.
     * this should always be used when user is created.
     */

    override fun addUser(userModel: UserModel): UserModel {
        userModel.apply {
            _id = userIds++
            _id?.let {
                allFakeUsers[it] = this
            }
            return this
        }
    }

    override fun updateUser(updatedUserModel: UserModel) {
        allFakeUsers[updatedUserModel._id!!] = updatedUserModel
    }



}