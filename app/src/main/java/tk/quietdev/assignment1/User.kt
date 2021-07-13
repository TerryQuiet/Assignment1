package tk.quietdev.assignment1



data class User(
    var userName: String? = null,
    var email: String,
    var occupation: String? = null,
    var physicalAddress: String? = null,
    var picture: Int = R.drawable.ic_profile,
    private var password: String
) {
    fun isPasswordCorrect(inputPassword: String): Boolean {
        return password == inputPassword
    }
}


