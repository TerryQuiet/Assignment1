package tk.quietdev.level1.database


import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.ext.readAssetsFile


class FakeDatabase(context: Context) {

    // all the users in database, using Int as ID
    private val  allFakeUsers =
        getUsersFromJson(context.assets.readAssetsFile("json/FakeUserArray.json"))

    // TODO: 8/2/2021 fix it later
    //var currentUserID: String = PrefsHelper.getCurrentUser()

 /*   private fun isPasswordCorrect(user: User?, password: String): Boolean {
        return (user?.password == password)
    }*/


    private fun getUsersFromJson(string: String): MutableMap<Int, User> {
        val mapper = jacksonObjectMapper()
        val userList:List<User> = mapper.readValue(string)
        return userList.mapIndexed { index, user ->  Pair(index, user.apply { id = index })}.toMap().toMutableMap()
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

    fun addUser(user: User) {
        user.apply {
            id = allFakeUsers.size
            id?.let {
                allFakeUsers[it] = this
            }
        }
    }

 /*   fun updateUser(oldUserID: String, user: User) {
        allFakeUsers[user.email] = user
        if (oldUserID != user.email) {
            allFakeUsers.remove(oldUserID)
        }
    }*/

}