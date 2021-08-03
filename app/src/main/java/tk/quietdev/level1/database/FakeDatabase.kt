package tk.quietdev.level1.database


import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.ext.readAssetsFile


class FakeDatabase(context: Context) {

    private var userIds: Int = 0

    // all the users in database, using Int as ID
    private val allFakeUsers: MutableMap<Int, User> = emptyMap<Int, User>().toMutableMap()

    init {
        addUsersFromJson(context.assets.readAssetsFile("json/FakeUserArray.json"))
    }


    // TODO: 8/2/2021 fix it later
    //var currentUserID: String = PrefsHelper.getCurrentUser()

    /*   private fun isPasswordCorrect(user: User?, password: String): Boolean {
           return (user?.password == password)
       }*/


    private fun addUsersFromJson(string: String) {
        val mapper = jacksonObjectMapper()
        val userList: List<User> = mapper.readValue(string)
        userList.forEach { addUser(it) }
    }

    /**
     * @param amount the number of users to return, -1 if all
     * @return list of users
     */
    fun getUserList(amount: Int = -1): List<User> {
        return allFakeUsers.values.take(if (amount < 0) allFakeUsers.size else amount)
    }

    /* fun getUserWithValidation(id: Int, password: String): User? {
         return if (isPasswordCorrect(allFakeUsers[id], password)) {
             allFakeUsers[id]
         } else null
     }*/

    fun getUserWithNoValidation(id: Int): User? {
        return allFakeUsers[id]
    }


    /**
     * @return user with Id assigned.
     * this should always be used when user is created.
     */
    fun addUser(user: User): User {
        user.apply {
            id = userIds++
            id?.let {
                allFakeUsers[it] = this
            }
            return this
        }
    }

    fun updateUser(updatedUser: User) {
        allFakeUsers[updatedUser.id!!] = updatedUser
    }

}