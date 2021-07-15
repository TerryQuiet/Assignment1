package tk.quietdev.level1.data

import tk.quietdev.level1.R


/**
 * Class with users so I can have some data to use for login
 */

class DB {
    private val userList = getUsersMap()

    fun getUser(name: String, password: String): User? {
        return if (userList[name]?.isPasswordCorrect(password) == true) {
            userList[name]
        } else null
    }

    private fun getUsersMap(): Map<String, User> {
        return mapOf(
            "mail@pm.me" to User(
                "Terry",
                "mail@pm.me",
                "Mega programmer",
                "Moon 23st",
                R.drawable.mulancircle,
                password = "11111"),
            "mail1@pm.me" to User("Quiet", "quiet@pm.me", password = "11111")
        )
    }
}