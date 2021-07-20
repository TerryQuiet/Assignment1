package tk.quietdev.level1.data

import tk.quietdev.level1.R


/**
 * Class with users so I can have some data to use for login
 */

class DB {
    private val userMap = getUsersMap()

    fun getUser(name: String, password: String): User? {
        return if (userMap[name]?.isPasswordCorrect(password) == true) {
            userMap[name]
        } else null
    }

    private fun getUsersMap(): Map<String, User> {
        return mapOf(
            "mail@pm.me" to User(
                "Terry",
                "mail@pm.me",
                "Mega programmer",
                "Moon 23st",
                "https://avatars.githubusercontent.com/u/12786477?v=4",
                password = "11111"),
            "mail1@pm.me" to User("Quiet", "mail1@pm.me"),
            "blabal@pm.me" to User("Blabal", "blabal@pm.me"),
            "xaxaax@pm.me" to User("Xaxaax", "xaxaax@pm.me"),
            "fffff1@pm.me" to User("Fffff1", "fffff1@pm.me"),
            "fffff2@pm.me" to User("Fffff2", "fffff2@pm.me"),
            "fffff3@pm.me" to User("Fffff3", "fffff3@pm.me"),
            "fffff4@pm.me" to User("Fffff4", "fffff4@pm.me"),
            "fffff5@pm.me" to User("Fffff5", "fffff5@pm.me"),
            "fffff6@pm.me" to User("Fffff6", "fffff6@pm.me"),
        )
    }

    fun getUserList(): List<User> {
       return userMap.values.toList()
    }
}