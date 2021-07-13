package tk.quietdev.assignment1


/**
 * Class with users so I can have some date to use for login
 */

class DB {
    private val userList = getUsersList()

    fun getUser(name: String, password: String): User? {
        return if (userList[name]?.isPasswordCorrect(password) == true) {
            userList[name]
        } else null
    }

    private fun getUsersList(): Map<String, User> {
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