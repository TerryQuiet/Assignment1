package tk.quietdev.level1.database


import com.fasterxml.jackson.module.kotlin.*
import tk.quietdev.level1.models.User
import tk.quietdev.level1.utils.PrefsHelper




object FakeDatabase {

    // all the users in database
    private lateinit var  allFakeUsers : MutableMap<String, User>

    var currentUserID: String = PrefsHelper.getCurrentUser()

    private fun isPasswordCorrect(user: User?, password: String): Boolean {
        return (user?.password == password)
    }

    fun init(string: String) {
        allFakeUsers = getUsersFromJson(string)
    }

    private fun getUsersFromJson(string: String): MutableMap<String, User> {
        val mapper = jacksonObjectMapper()
        val movieList:List<User> = mapper.readValue(string)
        return movieList.associateBy { it.email }.toMutableMap()
    }

    /**
     * @param amount the number of user emails to return, -1 if all
     * @return list of user emails
     */
    fun getUserList(amount: Int = -1): List<String> {
        return allFakeUsers.keys.take(if (amount < 0) allFakeUsers.size else amount)
    }

    fun getUserWithValidation(name: String, password: String): User? {
        return if (isPasswordCorrect(allFakeUsers[name], password)) {
            allFakeUsers[name]
        } else null
    }

    fun getUserWithNoValidation(name: String?): User? {
        return allFakeUsers[name]
    }

    fun addUser(user: User) {
        allFakeUsers[user.email] = user
    }

    fun updateUser(oldUserID: String, user: User) {
        allFakeUsers[user.email] = user
        if (oldUserID != user.email) {
            allFakeUsers.remove(oldUserID)
        }
    }

}