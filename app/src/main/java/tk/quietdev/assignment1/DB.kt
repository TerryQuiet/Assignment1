package tk.quietdev.assignment1

class DB {
    private val userList = getUsersList()

    fun getUser(name: String, password: String): User? {
        return if (userList[name]?.isPasswordCorrect(password) == true) {
            userList[name]
        } else null
    }

    private fun getUsersList(): Map<String, User> {
        return mapOf(
            "mail@pm.me" to User("Terry", "mail@pm.me", password = "11111"),
            "quiet@pm.me" to User("Quiet", "quiet@pm.me", password = "22222")
        )
    }
}